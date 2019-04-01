package com.jbg.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 1、Eurekaserver注册中心
 * @Package com.jbg.eurekaserver	
 * @author Lenovo
 * @date 2019年4月1日
 * @version V1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class Eurekaserver {
	public static void main(String[] args) {
		SpringApplication.run(Eurekaserver.class, args);
	}
}
