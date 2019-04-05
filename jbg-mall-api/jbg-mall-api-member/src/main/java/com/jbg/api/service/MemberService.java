package com.jbg.api.service;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jbg.base.ResponseBase;
import com.jbg.entity.MbUser;
/**
 * 1、MemberService业务逻辑层接口
 * 项目名称：jbg-mall-api-member 
 * 类名称：MemberService
 * 开发者：Lenovo
 * 开发时间：2019年4月1日下午12:49:45
 */
@RequestMapping(value="/member")
public interface MemberService {

	/**
	 * 1、根据用户id查询用户信息
	 * @return
	 */
	@PostMapping(value="/getUser")
	ResponseBase getUserById(Long userId);
	
	/**
	 * 2、注册用户
	 * @return
	 */
	@PostMapping(value="/insertUser")
	ResponseBase regUser(@RequestBody MbUser mbUser);
}
