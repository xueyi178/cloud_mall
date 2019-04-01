package com.jbg.base;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
/**
 * 1、redis的工具类
 * 项目名称：jbg-mall-common 
 * 类名称：BaseRedisService
 * 开发者：Lenovo
 * 开发时间：2019年4月1日上午11:38:57
 */
@Component
public class BaseRedisService {
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public void setString(String key, Object data, Long timeout) {
		if (data instanceof String) {
			String value = (String) data;
			stringRedisTemplate.opsForValue().set(key, value);
		}
		if (timeout != null) {
			stringRedisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
	}

	public Object getString(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	public void delKey(String key) {
		stringRedisTemplate.delete(key);
	}
}

