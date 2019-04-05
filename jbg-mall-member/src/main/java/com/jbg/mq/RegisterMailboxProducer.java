package com.jbg.mq;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
/**
 * 1、mq的生成者
 * 项目名称：jbg-mall-member 
 * 类名称：RegisterMailboxProducer
 * 开发者：Lenovo
 * 开发时间：2019年4月5日下午3:04:25
 */
@Service
public class RegisterMailboxProducer {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	/**
	 * 1、往mq里面推送消息
	 * @param destination：队列名字
	 * @param json：参数
	 */
	public void sendMsg(Destination destination, String json) {
		jmsMessagingTemplate.convertAndSend(destination, json);
	}
}
