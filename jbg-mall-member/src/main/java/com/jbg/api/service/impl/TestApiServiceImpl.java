package com.jbg.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import com.jbg.api.service.TestApiService;
import com.jbg.base.BaseApiService;
import com.jbg.base.ResponseBase;
/**
 * 1、接口地址
 * 项目名称：jbg-mall-member 
 * 类名称：TestApiServiceImpl
 * 开发者：Lenovo
 * 开发时间：2019年4月1日上午11:05:51
 */
@RestController
public class TestApiServiceImpl extends BaseApiService implements TestApiService {

	/**
	 * 1、测试的接口
	 * @param id
	 * @param name
	 * @return
	 */
	@Override
	public Map<String, Object> test(Integer id, String name) {
		Map<String, Object> map = new HashMap<>();
		map.clear();
		map.put("code", "200");
		map.put("mgs", "SUCCESS");
		map.put("data", new String[]{"haha","sdsd",id+"",name});
		return map;
	}

	/**
	 * 2、返回响应体
	 * @return
	 */
	@Override
	public ResponseBase testModelMap() {
		return this.setResultSuccess();
	}

	/**
	 * 3、测试redis
	 * @param key
	 * @param name
	 * @return
	 */
	@Override
	public ResponseBase testRedis(String key, String value) {
		this.baseRedisService.setString(key, value, null);
		return this.setResultSuccess();
	}

}
