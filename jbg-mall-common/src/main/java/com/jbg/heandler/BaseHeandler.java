package com.jbg.heandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jbg.base.BaseApiService;
import com.jbg.base.ResponseBase;

/**
 * 1、统一异常处理
 * 项目名称：jbg-mall-common 
 * 类名称：BaseHeandler
 * 开发者：Lenovo
 * 开发时间：2019年4月1日下午1:13:39
 */
@RestControllerAdvice
public class BaseHeandler {

	private BaseApiService baseApiService = new BaseApiService();

	@ExceptionHandler				
	public ResponseBase handleException(IllegalArgumentException e) {
		return baseApiService.setResultError(e.getMessage());
	}
}
