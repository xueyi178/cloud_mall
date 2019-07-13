package com.jbg.controller;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.jbg.base.ResponseBase;
import com.jbg.constants.Constants;
import com.jbg.entity.MbUser;
import com.jbg.fegin.MemberServiceFegin;
import com.jbg.utils.CookieUtil;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.oauth.Oauth;

/**
 * 1、登录的controlle
 * 项目名称：jbg-mall-pc-web 
 * 类名称：LoginController
 * 开发者：Lenovo
 * 开发时间：2019年4月7日上午11:00:02
 */
@Controller
public class LoginController {

	private static final String LOGIN = "login";

	private static final String INDEX = "redirect:/";

	private static final String qqrelation = "qqrelation";

	@Autowired
	private MemberServiceFegin memberServiceFegin;	
	/**
	 * 1、跳转到登录页面
	 * @return
	 */
	@GetMapping(value="/login")
	public String loginGet() {
		return LOGIN;
	}

	/**
	 * 2. 登录方法
	 * @param mbUser
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value="/login")
	public String loginPost(MbUser mbUser, HttpServletRequest request, HttpServletResponse response) {
		//1、验证参数
		Assert.notNull(mbUser.getUsername(), "用户名不能为空,请重新输入");
		Assert.notNull(mbUser.getPassword(), "密码不能为空,请重新输入");

		//2、调用登录接口,获取token的信息
		ResponseBase loginBase = memberServiceFegin.login(mbUser);
		if(!loginBase.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			request.setAttribute("error", "账号或密码错误");
			return LOGIN;
		}

		//3、将token信息保存到cookie里面
		LinkedHashMap<String, Object> loginData = (LinkedHashMap<String, Object>) loginBase.getData();
		String token = (String) loginData.get("token");
		if(StringUtils.isBlank(token)) {
			request.setAttribute("error", "会话已经失效");
			return LOGIN;
		}

		this.setCookie(token, response);
		return INDEX;
	}

	/**
	 * 封装存在cookie的代码
	 * @param memberToken
	 * @param response
	 */
	private void setCookie(String memberToken, HttpServletResponse response){
		CookieUtil.addCookie(response, Constants.COOKIE_MEMBER_TOKEN, memberToken, Constants.COOKIE_MEMBER_TIME);
	}

	/**
	 * 3. 生成QQ授权连接
	 * @return
	 * @throws QQConnectException 
	 */
	@RequestMapping(value="/locaQQLogin")
	public String locaQQLogin(HttpServletRequest request) throws QQConnectException {
		String authorizeURL = new Oauth().getAuthorizeURL(request);
		return "redirect:"+authorizeURL;
	}

	/**
	 * 4. 返回授权连接
	 * @param request
	 * @param response
	 * @param httpSession
	 * @return
	 * @throws QQConnectException
	 */
	@RequestMapping("/qqLoginCallback")
	public String qqLoginCallback(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession)
			throws QQConnectException {
		//1. 获取授权码code
		//2. 使用授权码Code获取accessToken 
		AccessToken accessTokenObj = new Oauth().getAccessTokenByRequest(request);
		if (accessTokenObj == null) {
			request.setAttribute("error", "qq授权失败!");
			return "error";
		}
		String accessToken = accessTokenObj.getAccessToken();
		if (StringUtils.isEmpty(accessToken)) {
			request.setAttribute("error", "qq授权失败!,accessToken不能为null");
			return "error";
		}
		// 3. 通过accessTokenObj获取openid
		OpenID openIdObj = new OpenID(accessToken);
		String userOpenID = openIdObj.getUserOpenID();
		//4. 根据openId去查询数据库是否存在
		ResponseBase openIdUser = memberServiceFegin.findByOpenIdUser(userOpenID);
		//4. 用戶沒有关联QQ账号
		if (openIdUser.getCode().equals(Constants.HTTP_RES_CODE_201)) {
			// 跳转到管理账号
			httpSession.setAttribute("qqOpenid", userOpenID);
			return qqrelation;
		}
		//5. 如果用户关联账号 直接登录,将用户登录信息存在在cookie中
		JSONObject data = (JSONObject) openIdUser.getData();
		String token = (String) data.get("token");
		this.setCookie(token, response);
		return INDEX;
	}

	/**
	 * 5. QQ登录的具体实现
	 * @param mbUser
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value="/qqRelation")
	public String qqRelation(MbUser mbUser, HttpServletRequest request, HttpServletResponse response, HttpSession httpSession) {
		//1、验证参数
		Assert.notNull(mbUser.getUsername(), "用户名不能为空,请重新输入");
		Assert.notNull(mbUser.getPassword(), "密码不能为空,请重新输入");

		//从session中获取openId
		String qqOpenid = (String) httpSession.getAttribute("qqOpenid");
		if(StringUtils.isEmpty(qqOpenid)) {
			request.setAttribute("error", "账户或者密码错误,请重试");
			return "error";
		}
		
		//2、调用登录接口,获取token的信息
		mbUser.setOpenId(qqOpenid);
		ResponseBase loginBase = memberServiceFegin.qqLogin(mbUser);
		if(!loginBase.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			request.setAttribute("error", "账号或密码错误");
			return LOGIN;
		}

		//3、将token信息保存到cookie里面
		LinkedHashMap<String, Object> loginData = (LinkedHashMap<String, Object>) loginBase.getData();
		String token = (String) loginData.get("token");
		if(StringUtils.isBlank(token)) {
			request.setAttribute("error", "会话已经失效");
			return LOGIN;
		}

		this.setCookie(token, response);
		return INDEX;
	}
}
