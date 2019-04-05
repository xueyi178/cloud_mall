package com.jbg.api.service.impl;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
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
	
}
