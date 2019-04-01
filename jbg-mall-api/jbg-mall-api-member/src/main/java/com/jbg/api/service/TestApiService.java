package com.jbg.api.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;

import com.jbg.base.ResponseBase;

/**
 * 1、对外提供的接口
 * 项目名称：jbg-mall-api-member 
 * 类名称：TestApiService
 * 开发者：Lenovo
 * 开发时间：2019年4月1日上午11:00:57
 */
@RequestMapping(value="/member")
public interface TestApiService {

	/**
	 * 1、测试的接口
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/test")
	Map<String, Object> test(Integer id, String name);
	
	/**
	 * 2、返回响应体
	 * @return
	 */
	@RequestMapping(value="/map")
	ResponseBase testModelMap(); 
	
	/**
	 * 3、测试redis
	 * @param key
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/testRedis")
	ResponseBase testRedis(String key, String value);
}
