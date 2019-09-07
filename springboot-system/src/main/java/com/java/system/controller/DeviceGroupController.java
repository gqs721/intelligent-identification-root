package com.java.system.controller;

import com.java.common.result.RestResult;
import com.java.model.domain.DeviceGroup;
import com.java.system.service.DeviceGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mr.BH
 */
@RestController
@RequestMapping("/device/group")
@Api(tags = "设备分组接口API", description="设备分组管理")
public class DeviceGroupController {

    @Autowired
    private DeviceGroupService deviceGroupService;

    /**
     * 添加分组信息
     * @param deviceGroup
     * @return
     */
    @ApiOperation(value = "添加分组信息接口", notes = "添加分组信息")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public RestResult save(DeviceGroup deviceGroup){
        return deviceGroupService.save(deviceGroup);
    }


    /**
     * 删除分组信息
     * @param groupId
     * @return
     */
    @ApiOperation(value = "删除分组信息接口", notes = "删除分组信息")
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public RestResult delete(Integer groupId){
        return deviceGroupService.delete(groupId);
    }


    /**
     * 修改分组信息
     * @param deviceGroup
     * @return
     */
    @ApiOperation(value = "修改分组信息接口", notes = "修改分组信息")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public RestResult update(DeviceGroup deviceGroup){
        return deviceGroupService.update(deviceGroup);
    }

    /**
     * 根据 管理员ID 获取分组列表
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据管理员id获取所有分组数据接口", notes = "根据管理员id获取分组，用于列表查看，参数：userId:整型")
    @RequestMapping(value = "/getAllByUserId",method = RequestMethod.GET)
    public RestResult getAllByUserId(@RequestParam Integer userId){
        return deviceGroupService.getAllByUserId(userId);
    }


}
