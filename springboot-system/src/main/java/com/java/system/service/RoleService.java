package com.java.system.service;

import com.java.common.result.RestResult;

import java.util.List;
import java.util.Map;

/**
 * Created by Mr.BH
 */
public interface RoleService {


    /**
     * 添加角色
     * @param map
     * @return
     */
    RestResult add(Map map);


    /**
     * 删除角色
     * @param roleIds
     * @return
     */
    RestResult delete(List<Integer> roleIds);


    /**
     * 修改
     * @param map
     * @return
     */
    RestResult update(Map map);


    /**
     * 角色分页数据
     * @param map
     * @return
     */
    RestResult listPage(Map map);


    /**
     * 获取角色列表
     * @return
     */
    RestResult getAll();


    /**
     * 角色 拥有的资源 Id 列表
     * @param roleId
     * @return
     */
    RestResult getRoleHaveResource(Integer roleId);


}
