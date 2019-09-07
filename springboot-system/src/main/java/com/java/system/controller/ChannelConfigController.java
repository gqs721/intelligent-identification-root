package com.java.system.controller;

import com.java.common.result.RestResult;
import com.java.model.domain.NvrConfig;
import com.java.system.service.ChannelConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * Created by Mr.BH
 */
@RestController
@RequestMapping("/channel/config")
@Api(tags = "通道配置接口API", description="通道配置管理")
public class ChannelConfigController {

    @Autowired
    private ChannelConfigService channelConfigService;


    /**
     * NVR配置分页数据
     * @param map
     * @return
     */
    @ApiOperation(value = "获取通道配置列表接口", notes = "获取通道配置列表，json串参数：keyword：关键字，nvrId：整型，pageNum:页码，pageSize：条数")
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public RestResult listPage(@RequestBody Map map){
        String keyword = (String) map.get("keyword");
        Integer nvrId = (Integer) map.get("nvrId");
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");
        return channelConfigService.listPage(keyword, nvrId, pageNum, pageSize);
    }

}
