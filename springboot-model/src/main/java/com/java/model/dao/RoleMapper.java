package com.java.model.dao;

import com.java.model.domain.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer pkId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer pkId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    // 自定义


    /**
     * 分页列表
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
     * 所有角色列表
     * @return
     */
    @Select("select sr.pk_id as roleId,sr.uk_name as ukName from sys_role as sr where sr.delete_flag = 0 and sr.pk_id != 2 order by sr.create_time desc")
    List<Map> getAll();


    /**
     * 获取角色用户的 资源列表 ID
     * 此处关联 resource 表 防止 资源被删除
     * @param roleId
     * @return
     */
    @Select("SELECT \n" +
            "  sr.`pk_id` \n" +
            "FROM\n" +
            "  sys_role_resource AS srr \n" +
            "  LEFT JOIN sys_resource AS sr \n" +
            "    ON srr.`resource_id` = sr.`pk_id` \n" +
            "WHERE srr.`role_id` = #{roleId}")
    List<Integer> getRoleHaveResourceListId(Integer roleId);


    /**
     * 获取 指定管理员 角色列表
     * @param adminId
     * @return
     */
//    @Select("")
    List<Role> getRolesByAdminId(int adminId);

    /**
     * 获取所有的角色
     * @return
     */
    List<Role> getAllRole();
}