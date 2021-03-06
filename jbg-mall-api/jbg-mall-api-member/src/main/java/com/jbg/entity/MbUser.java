package com.jbg.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
/**
 * 1、用户的实体类
 * 项目名称：jbg-mall-api-member 
 * 类名称：MbUser
 * 开发者：Lenovo
 * 开发时间：2019年4月1日下午12:48:14
 */
@Data
public class MbUser implements Serializable{
    /**
	 * 序列化
	 */
	private static final long serialVersionUID = 2880502973824370288L;

	private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Date created;

    private Date updated;

    private String openId;
}