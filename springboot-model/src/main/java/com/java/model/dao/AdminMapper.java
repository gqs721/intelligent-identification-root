package com.java.model.dao;

import com.java.model.domain.Admin;

import java.util.List;
import java.util.Map;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    // *** 自定义 ***

    Admin getByAuthenticatorUserName(String userName);

    Map getAdminByUserName(String userNmae);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteBatch(Integer[] ids);


    /**
     * 列表
     * @param queryMap
     * @return
     */
    List<Map> listPage(Map queryMap);

    /**
     * 统计
     * @param queryMap
     * @return
     */
    Integer countPage(Map queryMap);

    /**
     * 通过邮箱获取用户，看该邮箱是否已注册
     * @param email
     * @return
     */
    Admin selectByEmail(String email);

    /**
     * 通过用户名获取用户
     * @param email
     * @return
     */
    Admin selectByUserName(String email);

    /**
     * 超级管理员查询所有的用户不包括自己
     * @return
     */
    List<Map> findAllUser();
}