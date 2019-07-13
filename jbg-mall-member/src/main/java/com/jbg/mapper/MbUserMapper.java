package com.jbg.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    
    /**
     * 1. 用户登录
     * @param username
     * @param password
     * @return
     */
    @Select("select id, username, password, phone, email, created, updated, openId from mb_user where username = #{username} and password = #{password}")
    MbUser login(@Param("username")String username, @Param("password")String password);
    
    /**
     * 2. 根据openId去查询数据库
     * @param openId
     * @return
     */
    @Select("select id, username, password, phone, email, created, updated, openId from mb_user where openId = #{openId}")
    MbUser findByOpenIdUser(@Param("openId")String openId);
    
    /**
     * 3. 修改openid
     * @param openId
     * @return
     */
    @Update("update mb_user set openId = #{openId} where id = #{userId}")
    int updateByOpenIdUser(@Param("openId")String openId,@Param("userId") long userId);
}