package com.java.system.controller;

import com.java.common.result.RestResult;
import com.java.model.domain.ServerConfig;
import com.java.system.service.ServerConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * Created by Mr.BH
 */
@RestController
@RequestMapping("/server/config")
@Api(tags = "服务器配置接口API", description="服务器配置管理")
public class ServerConfigController {

    @Autowired
    private ServerConfigService serverConfigService;


    /**
     * 添加服务器配置
     * @param serverConfig
     * @return
     */
    @ApiOperation(value = "添加服务器配置接口", notes = "添加服务器配置")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public RestResult save(ServerConfig serverConfig){
        return serverConfigService.save(serverConfig);
    }


    /**
     * 删除服务器配置
     * @param map
     * @return
     */
    @ApiOperation(value = "删除、批量删除接口", notes = "删除、批量删除，json参数：ids：数组")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public RestResult delete(@RequestBody Map map){
        List<Integer> ids = (List<Integer>) map.get("ids");
        return serverConfigService.delete(ids);
    }


    /**
     * 修改服务器配置信息
     * @param serverConfig
     * @return
     */
    @ApiOperation(value = "修改服务器配置信息接口", notes = "修改服务器配置信息")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public RestResult update(ServerConfig serverConfig, BindingResult result){
        return serverConfigService.update(serverConfig);
    }


    /**
     * 获取服务器配置信息
     * @param id
     * @return
     */
    @ApiOperation(value = "获取服务器配置信息接口", notes = "获取服务器配置信息")
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public RestResult get( @RequestParam Integer id){
        return serverConfigService.getServerConfig(id);
    }


    /**
     * 通过用户id查询对应的服务器配置
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取通过用户id查询对应的服务器配置信息接口", notes = "获取通过用户id查询对应的服务器配置信息")
    @RequestMapping(value = "/findByUserId",method = RequestMethod.GET)
    public RestResult findByUserId( @RequestParam Integer userId){
        return serverConfigService.findByUserId(userId);
    }


    /**
     * 服务器配置分页数据
     * @param map
     * @return
     */
    @ApiOperation(value = "获取服务器配置列表接口", notes = "获取服务器配置列表，json串参数：keyword：关键字，userId：整型，pageNum:页码，pageSize：条数")
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public RestResult listPage(@RequestBody Map map){
        String keyword = (String) map.get("keyword");
        Integer userId = (Integer) map.get("userId");
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");
        return serverConfigService.listPage(keyword, userId, pageNum, pageSize);
    }

}
