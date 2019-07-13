package com.jbg.controller;

import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.jbg.base.ResponseBase;
import com.jbg.constants.Constants;
import com.jbg.fegin.MemberServiceFegin;
import com.jbg.utils.CookieUtil;

/**
 * 1、控制层
 * 项目名称：jbg-mall-pc-web 
 * 类名称：IndexController
 * 开发者：Lenovo
 * 开发时间：2019年4月6日下午4:08:00
 */
@Controller(value="")
public class IndexController {
	
	private static final String INDEX = "index";
	
	private static final String LOGIN = "login";
	
	@Autowired
	private MemberServiceFegin memberServiceFegin;	

	/**
	 * 1、访问首页
	 * 用/直接访问首页
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(HttpServletRequest request) {
		//1、从cookie中获取token的信息
		String token = CookieUtil.getUid(request, Constants.COOKIE_MEMBER_TOKEN);
		//2、如果cookie存在在token的情况下,调用会员服务接口,使用token查询用户信息
		if(StringUtils.isNoneBlank(token)) {
			ResponseBase loginToken = memberServiceFegin.getUserByToken(token);
			if(loginToken.getCode().equals(Constants.HTTP_RES_CODE_200)) {
				LinkedHashMap<String, Object> userData = (LinkedHashMap<String, Object>) loginToken.getData();
				String username = (String) userData.get("username");
				request.setAttribute("username", username);
				return INDEX;
			}
		}
		return LOGIN;
	}
}
