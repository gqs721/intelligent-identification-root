package com.java.system.controller;

import com.java.common.result.RestResult;
import com.java.model.domain.OfficiaAccountsConfig;
import com.java.system.service.OfficiaAccountsConfigService;
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
@RequestMapping("/officia/accounts/config")
@Api(tags = "微信公众号配置信息接口API", description="微信公众号配置信息管理")
public class OfficiaAccountsConfigController {

    @Autowired
    private OfficiaAccountsConfigService officiaAccountsConfigService;


    /**
     * 添加微信公众号配置信息
     * @param officiaAccountsConfig
     * @return
     */
    @ApiOperation(value = "添加微信公众号配置信息接口", notes = "添加微信公众号配置信息")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public RestResult save(OfficiaAccountsConfig officiaAccountsConfig){
        return officiaAccountsConfigService.save(officiaAccountsConfig);
    }


    /**
     * 修改微信公众号配置信息
     * @param officiaAccountsConfig
     * @return
     */
    @ApiOperation(value = "修改微信公众号配置信息接口", notes = "修改微信公众号配置信息")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public RestResult update(OfficiaAccountsConfig officiaAccountsConfig){
        return officiaAccountsConfigService.update(officiaAccountsConfig);
    }


    /**
     * 通过用户id查询对应的微信公众号配置信息
     * @param userId
     * @return
     */
    @ApiOperation(value = "通过用户id查询对应的微信公众号配置信息接口", notes = "通过用户id查询对应的微信公众号配置信息")
    @RequestMapping(value = "/selectByUserId",method = RequestMethod.GET)
    public RestResult selectByUserId( @RequestParam Integer userId){
        return officiaAccountsConfigService.selectByUserId(userId);
    }

}
