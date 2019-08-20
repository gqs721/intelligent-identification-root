package com.java.system.service;

import com.java.common.result.RestResult;
import com.java.model.domain.Admin;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr.BH
 */
public interface AdminService {


    /**
     * 登录
     * @param map
     * @return
     */
    RestResult login(Map map);


    /**
     * 退出
     * @param Authorization
     * @param request
     * @param response
     * @return
     */
    RestResult logout(String Authorization);


    /**
     * 添加 管理员
     * @param admin
     * @return
     */
    RestResult saveAdmin(Admin admin);


    /**
     * 批量添加 管理员
     * @param admins
     * @return
     */
    RestResult saveBatch(List<Admin> admins);


    /**
     * 根据 pkId  删除管理员
     * @param ids
     * @return
     */
    RestResult deleteAdmin(List<Integer> ids);


    /**
     * 修改 管理员信息
     * @param admin
     * @return
     */
    RestResult updateAdmin(Admin admin);


    /**
     * 获取管理员信息  根据主键ID\账户名
     * @param userId
     * @return
     */
    RestResult getAdmin(Integer userId);


    /**
     * 管理员列表
     * @param keyword
     * @param userType
     * @param pageNum
     * @param pageSize
     * @return
     */
    RestResult listAdmin(String keyword, Integer userType, Integer pageNum, Integer pageSize);


    /**
     * 修改管理员状态
     * @param id
     * @return
     */
    RestResult setStatus(Integer id);

    /**
     * 修改密码
     * @param userName
     * @param newPassword
     * @return
     */
    RestResult changePassword(String userName, String oldPassword, String newPassword);

    /**
     * 重置密码
     * @param id
     * @return
     */
    RestResult resetPassword(Integer id);

    /**
     * 通过邮箱获取用户，看该邮箱是否已注册
     * @param email
     * @return
     */
    Admin findByEmail(String email);

    /**
     * 重新设置密码
     * @param userName
     * @param newPassword
     * @return
     */
    RestResult resetPassword(String userName, String newPassword);

    /**
     * 超级管理员查询所有的用户
     * @return
     */
    RestResult findAllUser();
}
