package com.java.system.controller;

import com.java.common.result.RestResult;
import com.java.model.domain.NvrConfig;
import com.java.model.domain.ServerConfig;
import com.java.system.service.NvrConfigService;
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
@RequestMapping("/nvr/config")
@Api(tags = "NVR配置接口API", description="NVR配置管理")
public class NvrConfigController {

    @Autowired
    private NvrConfigService nvrConfigService;


    /**
     * 添加NVR配置
     * @param nvrConfig
     * @return
     */
    @ApiOperation(value = "添加NVR配置接口", notes = "添加NVR配置")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public RestResult save(NvrConfig nvrConfig){
        return nvrConfigService.save(nvrConfig);
    }


    /**
     * 删除NVR配置
     * @param map
     * @return
     */
    @ApiOperation(value = "删除、批量删除接口", notes = "删除、批量删除，json参数：ids：数组")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public RestResult delete(@RequestBody Map map){
        List<Integer> ids = (List<Integer>) map.get("ids");
        return nvrConfigService.delete(ids);
    }


    /**
     * 修改NVR配置信息
     * @param nvrConfig
     * @return
     */
    @ApiOperation(value = "修改NVR配置信息接口", notes = "修改NVR配置信息")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public RestResult update(NvrConfig nvrConfig){
        return nvrConfigService.update(nvrConfig);
    }


    /**
     * 获取NVR配置信息
     * @param id
     * @return
     */
    @ApiOperation(value = "获取NVR配置信息接口", notes = "获取NVR配置信息")
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public RestResult get( @RequestParam Integer id){
        return nvrConfigService.getNvrConfig(id);
    }


    /**
     * 修改NVR配置状态
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/setStatus",method = RequestMethod.GET)
    @ApiOperation(value = "修改NVR配置状态接口", notes = "修改NVR配置状态（禁用和启用）")
    public RestResult setStatus(@RequestParam Integer id){
        return nvrConfigService.setStatus(id);
    }


    /**
     * 通过用户id查询对应的NVR配置
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取通过用户id查询对应的NVR配置信息接口", notes = "获取通过用户id查询对应的NVR配置信息")
    @RequestMapping(value = "/findByUserId",method = RequestMethod.GET)
    public RestResult findByUserId( @RequestParam Integer userId){
        return nvrConfigService.findByUserId(userId);
    }


    /**
     * NVR配置分页数据
     * @param map
     * @return
     */
    @ApiOperation(value = "获取NVR配置列表接口", notes = "获取NVR配置列表，json串参数：keyword：关键字，userId：整型，pageNum:页码，pageSize：条数")
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public RestResult listPage(@RequestBody Map map){
        String keyword = (String) map.get("keyword");
        Integer userId = (Integer) map.get("userId");
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");
        return nvrConfigService.listPage(keyword, userId, pageNum, pageSize);
    }

}
