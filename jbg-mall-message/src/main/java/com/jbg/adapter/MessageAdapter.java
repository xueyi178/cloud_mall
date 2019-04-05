package com.jbg.adapter;

import com.alibaba.fastjson.JSONObject;

/***
 * 1、适配器接口,统一发送消息的接口
 * 项目名称：jbg-mall-message 
 * 类名称：MessageAdapter
 * 开发者：Lenovo
 * 开发时间：2019年4月5日下午12:19:54
 */
public interface MessageAdapter {

	/**
	 * 1、发送邮件的接口
	 * @param object
	 */
	public void sendMsg(JSONObject object);
}
