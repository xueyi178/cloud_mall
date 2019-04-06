package com.jbg.utils;

import java.util.UUID;
import com.jbg.constants.Constants;
/**
 * 1、生成token
 * 项目名称：jbg-mall-common 
 * 类名称：TokenUtils
 * 开发者：Lenovo
 * 开发时间：2019年4月6日上午10:43:24
 */
public class TokenUtils {
	public static String getToken(){
		return Constants.MEMBER_TOKEN+UUID.randomUUID();
	}

}
