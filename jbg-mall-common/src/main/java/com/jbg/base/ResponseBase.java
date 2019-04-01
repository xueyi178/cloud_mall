package com.jbg.base;

import lombok.Getter;
import lombok.Setter;

/**
 * 1、响应体
 * 项目名称：jbg-mall-common 
 * 类名称：ResponseBase
 * 开发者：Lenovo
 * 开发时间：2019年4月1日上午11:43:03
 */
@Getter
@Setter
public class ResponseBase {
	// 响应code
	private Integer code;
	// 消息内容
	private String msg;
	// 返回data
	private Object data;

	public ResponseBase() {
	}

	public ResponseBase(Integer code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
