package com.jbg.adapter.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jbg.adapter.MessageAdapter;
/**
 * 1、发送邮件的方法
 * 项目名称：jbg-mall-message 
 * 类名称：MessageAdapterImpl
 * 开发者：Lenovo
 * 开发时间：2019年4月5日下午12:40:40
 */
@Service
public class EmailServiceImpl implements MessageAdapter{

	private Logger log = LoggerFactory.getLogger(getClass());
	
	/**
	 * 1、发送邮件的接口
	 * @param object
	 */
	@Override
	public void sendMsg(JSONObject object) {
		//处理发送邮件的
		String email = object.getString("email");
		if(StringUtils.isEmpty(email)) {
			return;
		}
		//
		log.info("####消息服务平台发送邮件账号:{}"+email);
	}
}
