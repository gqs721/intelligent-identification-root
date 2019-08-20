package com.java.system.controller;

import com.java.common.result.RestResult;
import com.java.model.domain.StreamMediaConfig;
import com.java.system.service.StreamMediaConfigService;
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
@RequestMapping("/stream/media/config")
@Api(tags = "流媒体配置接口API", description="流媒体配置管理")
public class StreamMediaConfigController {

    @Autowired
    private StreamMediaConfigService streamMediaConfigService;


    /**
     * 添加流媒体配置
     * @param streamMediaConfig
     * @return
     */
    @ApiOperation(value = "添加流媒体配置接口", notes = "添加流媒体配置")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public RestResult save(StreamMediaConfig streamMediaConfig){
        return streamMediaConfigService.save(streamMediaConfig);
    }


    /**
     * 删除流媒体配置
     * @param map
     * @return
     */
    @ApiOperation(value = "删除、批量删除接口", notes = "删除、批量删除，json参数：ids：数组")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public RestResult delete(@RequestBody Map map){
        List<Integer> ids = (List<Integer>) map.get("ids");
        return streamMediaConfigService.delete(ids);
    }


    /**
     * 修改流媒体配置信息
     * @param streamMediaConfig
     * @return
     */
    @ApiOperation(value = "修改流媒体配置信息接口", notes = "修改流媒体配置信息")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public RestResult update(StreamMediaConfig streamMediaConfig, BindingResult result){
        return streamMediaConfigService.update(streamMediaConfig);
    }


    /**
     * 获取流媒体配置信息
     * @param id
     * @return
     */
    @ApiOperation(value = "获取流媒体配置信息接口", notes = "获取流媒体配置信息")
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public RestResult get( @RequestParam Integer id){
        return streamMediaConfigService.getStreamMediaConfig(id);
    }


    /**
     * 通过用户id查询对应的流媒体配置
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取通过用户id查询对应的流媒体配置信息接口", notes = "获取通过用户id查询对应的流媒体配置信息")
    @RequestMapping(value = "/findByUserId",method = RequestMethod.GET)
    public RestResult findByUserId( @RequestParam Integer userId){
        return streamMediaConfigService.findByUserId(userId);
    }


    /**
     * 流媒体配置分页数据
     * @param map
     * @return
     */
    @ApiOperation(value = "获取流媒体配置列表接口", notes = "获取流媒体配置列表，json串参数：keyword：关键字，pageNum:页码，pageSize：条数")
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public RestResult listPage(@RequestBody Map map){
        String keyword = (String) map.get("keyword");
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");
        return streamMediaConfigService.listPage(keyword, pageNum, pageSize);
    }

}
