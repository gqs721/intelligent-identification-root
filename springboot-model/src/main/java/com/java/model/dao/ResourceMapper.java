package com.java.model.dao;

import com.java.model.domain.Resource;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ResourceMapper {
    int deleteByPrimaryKey(Integer pkId);

    int insert(Resource record);

    int insertSelective(Resource record);

    Resource selectByPrimaryKey(Integer pkId);

    int updateByPrimaryKeySelective(Resource record);

    int updateByPrimaryKey(Resource record);

    // 自定义


    /**
     * 根据 名称 查找
     * @param ukName
     * @return
     */
    @Select("select * from sys_resource where uk_name=#{ukName} and delete_flag=0")
    Resource getResourceByUkName(String ukName);

    @Select("select pk_id from sys_resource where parent_id=#{parentId} and delete_flag=0")
    List<Integer> selectChildpkIds(Integer parentId);


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
     * 获取所有资源列表
     * @return
     */
    @Select("select " +
            "pk_id as pkId," +
            "parent_id as parentId, " +
            "uk_name as title, " +
            "path," +
            "perms," +
            "type," +
            "order_num as orderNum from sys_resource where delete_flag=0 order by orderNum ")
    List<Map> getAll();

    @Select("select pk_id as pkId, uk_name as ukName from sys_resource where delete_flag=0 and type=0 order by order_num;")
    List<Map> parentList();


    /**
     * 获取 指定角色的 权限列表
     * @param roleIds
     * @return
     */
    List<Resource> getResourcesByRoleId(List<Integer> roleIds);

    /**
     * 获取所有的菜单
     * @return
     */
    List<Resource> getAllMenus();
}