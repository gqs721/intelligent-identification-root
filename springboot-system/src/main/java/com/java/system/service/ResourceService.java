package com.java.system.service;

import com.java.common.result.RestResult;

import java.util.Map;

/**
 * Created by Mr.BH on 2018/7/27.
 */
public interface ResourceService {


    /**
     * 添加 一级菜单/二级列表
     * @param map
     * @return
     */
    RestResult save(Map map);

    /**
     * 删除
     * @param pkId
     * @return
     */
    RestResult delete(Integer pkId);

    /**
     * 修改资源信息
     * @param map
     * @return
     */
    RestResult update(Map map);


    /**
     * 一级菜单 列表
     * @return
     */
    RestResult parentList();


    /**
     * 获取所有资源列表
     * @return
     */
    RestResult getAll();


    /**
     * 获取菜单列表
     * @param adminId
     * @return
     */
    RestResult getMenusByAdminId(Integer adminId);

}
