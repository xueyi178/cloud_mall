package com.jbg.fegin;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

import com.jbg.api.service.MemberService;

/**
 * 1、使用feigin调用
 * 项目名称：jbg-mall-pc-web 
 * 类名称：MemberServiceFegin
 * 开发者：Lenovo
 * 开发时间：2019年4月6日下午4:49:16
 */
@Component
@FeignClient(name="member")
public interface MemberServiceFegin extends MemberService{

}
