package com.jbg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 1、pc端的服务启动类
 * 项目名称：jbg-mall-pc-web 
 * 类名称：ApplicationWeb
 * 开发者：Lenovo
 * 开发时间：2019年4月6日下午4:13:34
 */
@SpringBootApplication
@EnableEurekaClient
public class ApplicationWeb {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationWeb.class, args);
	}
}
