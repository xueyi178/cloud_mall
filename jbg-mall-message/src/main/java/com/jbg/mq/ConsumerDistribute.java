package com.jbg.mq;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jbg.adapter.MessageAdapter;
import com.jbg.adapter.impl.EmailServiceImpl;
import com.jbg.constants.Constants;

/**
 * 1、消费者
 * 项目名称：jbg-mall-message 
 * 类名称：ConsumerDistribute
 * 开发者：Lenovo
 * 开发时间：2019年4月5日下午12:20:53
 */
@Component
public class ConsumerDistribute {
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	private MessageAdapter messageAdapter;
	
	@Autowired
	private EmailServiceImpl messageAdapterImpl;
	
	/*@Value("${messages.queue}")
	private String MESSAGESQUEUE; */

	/**
	 *	1、监听消息
	 * destination="messages_queue"：表示监听那一个队列的消息
	 * @param json：表示消息内容
	 */
	@JmsListener(destination="messages_queue")
	public void distribute(String json) {
		log.info("######消息服务平台接收消息的内容:{}######",json);
		if(StringUtils.isEmpty(json)) {
			return;
		}
		
		//把字符串json转换成json对象
		JSONObject rootJson = new JSONObject().parseObject(json);
		//获取header参数
		JSONObject header = rootJson.getJSONObject("header");
		//获取接口的类型
		String interfaceType = header.getString("interfaceType");
		if(StringUtils.isEmpty(interfaceType)) {
			return;
		}
		//判断接口类型是否是发邮件的接口
		if(interfaceType.equals(Constants.MSG_SMS)) {
			// 调用第三方的邮件接口
			messageAdapter = messageAdapterImpl;
		}
		if(messageAdapter == null) {
			return;
		}
		//取出发送的内容接口
		JSONObject contentJson = rootJson.getJSONObject("content");
		messageAdapter.sendMsg(contentJson);
	}
}
