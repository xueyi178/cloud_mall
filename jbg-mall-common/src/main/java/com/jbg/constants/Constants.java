package com.jbg.constants;
/**
 * 1、响应的code
 * 项目名称：jbg-mall-common 
 * 类名称：Constants
 * 开发者：Lenovo
 * 开发时间：2019年4月1日上午11:44:34
 */
public interface Constants {
	// 响应code
	String HTTP_RES_CODE_NAME = "code";
	// 响应msg
	String HTTP_RES_CODE_MSG = "msg";
	// 响应data
	String HTTP_RES_CODE_DATA = "data";
	// 响应请求成功
	String HTTP_RES_CODE_200_VALUE = "success";
	// 系统错误
	String HTTP_RES_CODE_500_VALUE = "fial";
	// 响应请求成功code
	Integer HTTP_RES_CODE_200 = 200;
	// 系统错误
	Integer HTTP_RES_CODE_500 = 500;

	// 发送邮件
	String MSG_SMS = "email";

	//生成token,会员token
	String MEMBER_TOKEN = "MEMBER_TOKEN";

	//token在redis中的有效期
	Long TOKEN_MEMBER_TIME = (long) (60*60*24*90);

	//token在redis中的有效期
	int COOKIE_MEMBER_TIME = 60*60*24*90;

	//会员token的名称
	String COOKIE_MEMBER_TOKEN = "cookie_member_token";
}
