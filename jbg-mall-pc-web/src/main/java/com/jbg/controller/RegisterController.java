package com.jbg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 1、注册的controller
 * 项目名称：jbg-mall-pc-web 
 * 类名称：RegisterController
 * 开发者：Lenovo
 * 开发时间：2019年4月6日下午4:23:30
 */
@Controller
public class RegisterController {

	private static final String REGISTER = "register";
	
	/**
	 * 1、跳转到注册页面
	 * @return
	 */
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String register() {
		return REGISTER;
	}
}
