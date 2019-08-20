package com.java.system.controller;

import com.java.common.result.RestResult;
import com.java.system.service.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Mr.BH
 */
@RestController
@RequestMapping("/sys/menus")
@Api(tags = "菜单接口API", description="菜单管理")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 添加 一级菜单/二级列表
     * @param map
     * @return
     */
    @ApiOperation(value = "添加菜单接口", notes = "添加菜单")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public RestResult save(@RequestBody Map map){
        return resourceService.save(map);
    }


    /**
     * 删除 一级菜单/二级列表
     * @param map
     * @return
     */
    @ApiOperation(value = "删除菜单接口", notes = "删除一级菜单/二级列表")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public RestResult delete(@RequestBody Map map){
        Integer pkId = (Integer) map.get("pkId");
        return resourceService.delete(pkId);
    }


    /**
     * 修改 菜单/列表 信息
     * @param map
     * @return
     */
    @ApiOperation(value = "修改菜单接口", notes = "修改菜单")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public RestResult update(@RequestBody Map map){
        return resourceService.update(map);
    }


    /**
     * 一级菜单 列表
     * @return
     */
    @ApiOperation(value = "获取父级菜单接口", notes = "获取父级菜单")
    @RequestMapping(value = "/parentList",method = RequestMethod.POST)
    public RestResult list(){
        return resourceService.parentList();
    }


    /**
     * 获取所有资源列表
     * @return
     */
    @ApiOperation(value = "获取全部菜单接口", notes = "获取全部菜单")
    @RequestMapping(value = "/all",method = RequestMethod.POST)
    public RestResult getAll(){
        return resourceService.getAll();
    }

    /**
     * 根据 管理员ID 获取菜单列表
     * @param map
     * @return
     */
    @ApiOperation(value = "根据管理员id获取菜单接口", notes = "根据管理员id获取菜单，json参数：adminId:整型")
    @RequestMapping(value = "/resource",method = RequestMethod.POST)
    public RestResult getMenusByAdminId(@RequestBody Map map){
        return resourceService.getMenusByAdminId((Integer) map.get("adminId"));
    }


}
