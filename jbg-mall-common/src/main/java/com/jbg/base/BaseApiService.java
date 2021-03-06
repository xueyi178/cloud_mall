package com.jbg.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jbg.constants.Constants;
/**
 * 1、BaseService
 * 项目名称：jbg-mall-common 
 * 类名称：BaseService
 * 开发者：Lenovo
 * 开发时间：2019年4月1日上午11:46:33
 */
@Component
public class BaseApiService {
	
	@SuppressWarnings("unused")
	@Autowired
	public BaseRedisService baseRedisService;
	
	// 返回成功 ,data值为null
	public ResponseBase setResultSuccess() {
		return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null);
	}
	// 返回成功 ,data可传
	public ResponseBase setResultSuccess(Object data) {
		return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data);
	}
	// 返回失败
	public ResponseBase setResultError(String msg){
		return setResult(Constants.HTTP_RES_CODE_500,msg, null);
	}
	
	//返回失败的
	public ResponseBase setResultError(Integer code, String msg){
		return setResult(Constants.HTTP_RES_CODE_201,msg, null);
	}
	// 自定义返回结果
	public ResponseBase setResult(Integer code, String msg, Object data) {
		ResponseBase responseBase = new ResponseBase();
		responseBase.setCode(code);
		responseBase.setMsg(msg);
		if (data != null)
			responseBase.setData(data);
		return responseBase;
	}
}
