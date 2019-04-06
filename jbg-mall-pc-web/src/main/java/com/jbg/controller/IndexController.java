package com.jbg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	/**
	 * 1、访问首页
	 * @return
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index() {
		return INDEX;
	}
}
