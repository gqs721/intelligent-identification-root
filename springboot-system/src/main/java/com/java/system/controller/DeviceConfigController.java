package com.java.system.controller;

import com.java.common.result.RestResult;
import com.java.model.domain.DeviceConfig;
import com.java.system.service.DeviceConfigService;
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
@RequestMapping("/device/config")
@Api(tags = "设备配置接口API", description="设备配置管理")
public class DeviceConfigController {

    @Autowired
    private DeviceConfigService deviceConfigService;


    /**
     * 添加设备配置
     * @param deviceConfig
     * @return
     */
    @ApiOperation(value = "添加设备配置接口", notes = "添加设备配置")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public RestResult save(DeviceConfig deviceConfig){
        return deviceConfigService.save(deviceConfig);
    }


    /**
     * 删除设备配置
     * @param map
     * @return
     */
    @ApiOperation(value = "删除、批量删除接口", notes = "删除、批量删除，json参数：ids：数组")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public RestResult delete(@RequestBody Map map){
        List<Integer> ids = (List<Integer>) map.get("ids");
        return deviceConfigService.delete(ids);
    }


    /**
     * 修改设备配置信息
     * @param deviceConfig
     * @return
     */
    @ApiOperation(value = "修改设备配置信息接口", notes = "修改设备配置信息")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public RestResult update(DeviceConfig deviceConfig, BindingResult result){
        return deviceConfigService.update(deviceConfig);
    }


    /**
     * 修改设备状态
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/setStatus",method = RequestMethod.GET)
    @ApiOperation(value = "修改设备状态接口", notes = "修改设备状态（禁用和启用）")
    public RestResult setStatus(@RequestParam Integer id){
        return deviceConfigService.setStatus(id);
    }


    /**
     * 获取设备配置信息
     * @param id
     * @return
     */
    @ApiOperation(value = "获取设备配置信息接口", notes = "获取设备配置信息")
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public RestResult get( @RequestParam Integer id){
        return deviceConfigService.getDeviceConfig(id);
    }


    /**
     * 通过userId获取设备配置信息
     * @param userId
     * @param deviceName
     * @return
     */
    @ApiOperation(value = "获取公司下的所有设备配置信息接口", notes = "用于监控视频页面，监控设备的查询")
    @RequestMapping(value = "/findByUserId",method = RequestMethod.GET)
    public RestResult findByUserId( @RequestParam Integer userId, @RequestParam String deviceName){
        return deviceConfigService.findByUserId(userId, deviceName);
    }


    /**
     * 开始直播
     * @param deviceId
     * @return
     */
    @ApiOperation(value = "开始直播接口", notes = "开始直播")
    @RequestMapping(value = "/streamStart",method = RequestMethod.GET)
    public RestResult streamStart( @RequestParam Integer deviceId){
        return deviceConfigService.streamStart(deviceId);
    }


    /**
     * 停止直播
     * @param deviceId
     * @return
     */
    @ApiOperation(value = "停止直播接口", notes = "停止直播")
    @RequestMapping(value = "/streamStop",method = RequestMethod.GET)
    public RestResult streamStop( @RequestParam Integer deviceId){
        return deviceConfigService.streamStop(deviceId);
    }


    /**
     * 设备配置分页数据
     * @param map
     * @return
     */
    @ApiOperation(value = "获取设备配置列表接口", notes = "获取设备配置列表，json串参数：keyword：关键字，userId:整型，pageNum:页码，pageSize：条数")
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public RestResult listPage(@RequestBody Map map){
        String keyword = (String) map.get("keyword");
        Integer userId = (Integer) map.get("userId");
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");
        return deviceConfigService.listPage(keyword, userId, pageNum, pageSize);
    }

}
