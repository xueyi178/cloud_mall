package com.jbg.api.service.impl;

import java.util.Date;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jbg.api.service.MemberService;
import com.jbg.base.BaseApiService;
import com.jbg.base.ResponseBase;
import com.jbg.constants.Constants;
import com.jbg.entity.MbUser;
import com.jbg.mapper.MbUserMapper;
import com.jbg.mq.RegisterMailboxProducer;
import com.jbg.utils.MD5Utils;
import com.jbg.utils.TokenUtils;
/**
 * 1、member业务逻辑层实现类
 * 项目名称：jbg-mall-member 
 * 类名称：MemberServiceImpl
 * 开发者：Lenovo
 * 开发时间：2019年4月1日下午12:51:18
 */
@RestController
public class MemberServiceImpl extends BaseApiService implements MemberService {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private MbUserMapper mbUserMapper;

	@Autowired
	private RegisterMailboxProducer registerMailboxProducer;

	@Value("${messages.queue}")
	private String MESSAGESQUEUE;

	/**
	 * 1、根据用户id查询用户信息
	 * @return
	 */
	@Override
	public ResponseBase getUserById(Long userId) {
		Assert.notNull(userId, "用户id能为空");
		MbUser user = mbUserMapper.selectByPrimaryKey(userId);
		if(user == null) {
			return this.setResultError("查询用户信息失败");
		}
		return this.setResultSuccess(user);
	}

	/**
	 * 2、注册用户
	 * @return
	 */
	@Override
	public ResponseBase regUser(@RequestBody MbUser mbUser) {
		Assert.notNull( mbUser.getPassword(), "用户密码不能为空");
		//参数校验
		String password = mbUser.getPassword();
		String md5 = MD5Utils.MD5(password);
		mbUser.setPassword(md5);
		mbUser.setCreated(new Date());
		mbUser.setUpdated(new Date());
		int falg = mbUserMapper.insert(mbUser);
		if(falg <= 0) {
			return this.setResultError("注册用户信息失败");
		}

		//采用mq发送消息
		//传邮箱地址
		String emailJson = emailJson(mbUser.getEmail());
		log.info("####会员服务推送消息到消息服务平台####json{}"+emailJson);
		this.sendMsg(emailJson);
		return this.setResultSuccess("用户注册成功");
	}

	/**
	 * 封装参数
	 * @param email
	 * @return
	 */
	private String emailJson(String email) {
		JSONObject rootJson = new JSONObject();
		JSONObject header = new JSONObject();
		header.put("interfaceType", Constants.MSG_SMS);
		JSONObject content = new JSONObject();
		content.put("email", email);
		rootJson.put("header", header);
		rootJson.put("content", content);
		return rootJson.toJSONString();
	}

	/**
	 * 发送消息
	 * @param json
	 */
	private void sendMsg(String json) {
		//创建一个消息对象
		ActiveMQQueue activeMQQueue = new ActiveMQQueue(MESSAGESQUEUE);
		registerMailboxProducer.sendMsg(activeMQQueue, json);
	}

	/**
	 * 3、用户登录的接口
	 * @param mbUser
	 * @return
	 */
	@Override
	public ResponseBase login(@RequestBody MbUser mbUser) {
		//1、验证参数
		Assert.notNull(mbUser.getUsername(), "用户名不能为空,请重新输入");
		Assert.notNull(mbUser.getPassword(), "密码不能为空,请重新输入");
		//2、数据库查询用户名和密码是否正确
		String password = MD5Utils.MD5(mbUser.getPassword());
		mbUser.setPassword(password);
		return this.setLogin(mbUser);
	}

	/**
	 * 4、使用token查询用户信息
	 */
	@Override
	public ResponseBase getUserByToken(@RequestParam("token") String token) {
		//1、验证参数
		Assert.notNull(token,"token不能为空");
		//2、使用token从redis查询userId
		String userId = (String) this.baseRedisService.getString(token);
		Assert.notNull(userId,"token无效或者已过期了");
		//3、使用userId查询用户信息,返回给客户端
		MbUser mbUser = mbUserMapper.selectByPrimaryKey(Long.valueOf(userId));
		Assert.notNull(mbUser,"查询错误,请重试");
		MbUser user = new MbUser();
		user.setUsername(mbUser.getUsername());
		user.setEmail(mbUser.getEmail());
		user.setPhone(mbUser.getPhone());
		return this.setResultSuccess(user);
	}

	/**
	 * 封装登录的方法
	 * @param mbUser
	 * @param password
	 * @return
	 */
	private ResponseBase setLogin(MbUser mbUser) {
		//1、验证参数
		Assert.notNull(mbUser.getUsername(), "用户名不能为空,请重新输入");
		Assert.notNull(mbUser.getPassword(), "密码不能为空,请重新输入");
		MbUser user = mbUserMapper.login(mbUser.getUsername(), mbUser.getPassword());
		Assert.notNull(user, "账号或密码不正确,请重试");
		//3、如果账号和密码正确,生成token
		String token = TokenUtils.getToken();
		//4、存放到redis中key为token,value：userId,
		log.info("####用户信息存入到redis中.....key为:{}"+token,"value为:{}"+user.getId());
		this.baseRedisService.setString(token, user.getId().toString(), Constants.TOKEN_MEMBER_TIME);
		//5、直接返回token
		JSONObject object = new JSONObject();
		object.put("token", token);
		object.put("id", user.getId());
		return setResultSuccess(object);
	}


	/**
	 * 5. 使用openId获取用户信息
	 * @param openId
	 * @return
	 */
	@Override
	public ResponseBase findByOpenIdUser(@RequestParam("openId") String openId) {
		//1. 判断openid是否为空
		Assert.notNull(openId, "openId不能为空");
		//2. 使用openId去查询数据库
		MbUser mbUser = mbUserMapper.findByOpenIdUser(openId);
		if(mbUser == null) {
			return setResultError(Constants.HTTP_RES_CODE_201, "该openId没有关联");
		}
		//自动登录
		return this.setLogin(mbUser);
	}

	/**
	 * 6. 使用qq来进行登录
	 * @param mbUser
	 * @return
	 */
	@Override
	public ResponseBase qqLogin(@RequestBody MbUser mbUser) {
		//1. 验证参数
		Assert.notNull(mbUser.getUsername(), "用户名不能为空,请重新输入");
		Assert.notNull(mbUser.getPassword(), "密码不能为空,请重新输入");
		//2. 先进行账号登录,修改数据库中对应的openId
		ResponseBase setLogin = this.login(mbUser);
		//3. 如果登录成功,
		if(!setLogin.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			return setLogin;
		}
		 
		JSONObject data = (JSONObject) setLogin.getData();
		Long id = (Long) data.get("id");
		int falg = mbUserMapper.updateByOpenIdUser(mbUser.getOpenId(),id);
		if(falg <= 0) {
			return setResultError("QQ账号关联失败");
		}
		return setLogin;
	}
}
