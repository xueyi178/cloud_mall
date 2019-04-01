package com.jbg.mapper;

import com.jbg.entity.MbUser;
/**
 * 1、用户的数据访问层接口
 * 项目名称：jbg-mall-member 
 * 类名称：MbUserMapper
 * 开发者：Lenovo
 * 开发时间：2019年4月1日下午1:03:55
 */
public interface MbUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MbUser record);

    int insertSelective(MbUser record);

    MbUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MbUser record);

    int updateByPrimaryKey(MbUser record);
}