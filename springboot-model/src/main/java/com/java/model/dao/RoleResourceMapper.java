package com.java.model.dao;

import com.java.model.domain.RoleResource;
import org.apache.ibatis.annotations.Delete;

public interface RoleResourceMapper {
    int deleteByPrimaryKey(Integer pkId);

    int insert(RoleResource record);

    int insertSelective(RoleResource record);

    RoleResource selectByPrimaryKey(Integer pkId);

    int updateByPrimaryKeySelective(RoleResource record);

    int updateByPrimaryKey(RoleResource record);

    // **** 自定义 ****

    @Delete("delete from sys_role_resource where role_id=#{roleId}")
    int deleteByRoleId(Integer roleId);

    @Delete("delete from sys_role_resource where resource_id=#{resourceId}")
    int deleteByResourceId(Integer resourceId);



}