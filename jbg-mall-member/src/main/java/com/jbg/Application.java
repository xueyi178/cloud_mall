package com.jbg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.jms.annotation.EnableJms;

/**
 * 1、Eurekaserver客户端启动类
 * 项目名称：jbg-mall-member 
 * 类名称：Application
 * 开发者：Lenovo
 * 开发时间：2019年4月1日上午11:04:32
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.jbg.mapper")
@EnableJms
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
