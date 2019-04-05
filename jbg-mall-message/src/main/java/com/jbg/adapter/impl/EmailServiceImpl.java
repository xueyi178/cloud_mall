package com.jbg.adapter.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	
	@Value("${msg.subject}")
	private String subject;
	
	@Value("${msg.text}")
	private String text;

	@Autowired
	private JavaMailSender mailSender;
	
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
		log.info("####消息服务平台发送邮件:{}开始"+email);
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		//来自账号
		mailMessage.setFrom(email);
		//发送账号
		mailMessage.setTo(email);
		//标题
		mailMessage.setSubject(subject);
		//内容
		mailMessage.setText(text.replace("{}", email));
		//发送邮件
		mailSender.send(mailMessage);
		log.info("####消息服务平台发送邮件:{}结束"+email);
	}
}
