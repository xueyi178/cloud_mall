package com.jbg.controller;

import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jbg.base.ResponseBase;
import com.jbg.constants.Constants;
import com.jbg.entity.MbUser;
import com.jbg.fegin.MemberServiceFegin;
import com.jbg.utils.CookieUtil;

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
		
		CookieUtil.addCookie(response, Constants.COOKIE_MEMBER_TOKEN, token, Constants.COOKIE_MEMBER_TIME);
		return INDEX;
	}
}
