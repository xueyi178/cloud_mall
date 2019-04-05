package com.jbg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 1、启动类
 * 项目名称：jbg-mall-message 
 * 类名称：Application
 * 开发者：Lenovo
 * 开发时间：2019年4月5日下午3:51:05
 */
@SpringBootApplication
@EnableEurekaClient
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
