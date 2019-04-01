package com.jbg.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestController;

import com.jbg.api.service.MemberService;
import com.jbg.base.BaseApiService;
import com.jbg.base.ResponseBase;
import com.jbg.entity.MbUser;
import com.jbg.mapper.MbUserMapper;
/**
 * 1、member业务逻辑层实现类
 * 项目名称：jbg-mall-member 
 * 类名称：MemberServiceImpl
 * 开发者：Lenovo
 * 开发时间：2019年4月1日下午12:51:18
 */
@RestController
public class MemberServiceImpl extends BaseApiService implements MemberService {

	@Autowired
	private MbUserMapper mbUserMapper;
	
	@Override
	public ResponseBase getUserById(Long userId) {
		Assert.notNull(userId, "用户id能为空");
		MbUser user = mbUserMapper.selectByPrimaryKey(userId);
		if(user == null) {
			return this.setResultError("查询用户信息失败");
		}
		return this.setResultSuccess(user);
	}

}
