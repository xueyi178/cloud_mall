package com.jbg.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.jbg.base.ResponseBase;
import com.jbg.constants.Constants;
import com.jbg.entity.MbUser;
import com.jbg.fegin.MemberServiceFegin;

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
	private static final String LOGIN = "login";
	
	@Autowired
	private MemberServiceFegin memberServiceFegin;
	
	/**
	 * 1、跳转到注册页面
	 * @return
	 */
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String register() {
		return REGISTER;
	}
	
	/**
	 * 2、注册业务具体实现
	 * @return
	 */
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerPost(MbUser mbUser, HttpServletRequest request) {
		//1、验证参数
		Assert.notNull(mbUser.getUsername(), "用户名不能为空,请输入用户名");
		Assert.notNull(mbUser.getPassword(), "密码不能为空,请输入密码");
		//2、调用会员的注册接口
		ResponseBase regUser = memberServiceFegin.regUser(mbUser);
		//3、如果是失败了,跳转到失败的页面
		if(!regUser.getCode().equals(Constants.HTTP_RES_CODE_200)) {
			request.setAttribute("error", "注册失败,请重试");
			return REGISTER;
		}
		//4、如果成功,跳转到成功的页面
		return LOGIN;
	}
}
