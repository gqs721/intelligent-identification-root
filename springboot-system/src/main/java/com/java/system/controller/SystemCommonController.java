package com.java.system.controller;

import com.java.common.result.RestResult;
import com.java.system.service.SystemCommonService;
import io.swagger.annotations.Api;
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
@RequestMapping("/sys/common")
@Api(tags = "系统通用接口API", description="系统通用接口管理")
public class SystemCommonController {

    @Autowired
    private SystemCommonService systemCommonService;


    /**
     * 添加openId
     * @param map
     * @return
     */
    @RequestMapping(value = "/saveWeixinOpenId",method = RequestMethod.POST)
    public RestResult save(@RequestBody Map map){
        String openId = (String) map.get("open_id");
        return systemCommonService.savePushWeixin(openId);
    }


    /**
     * 解绑openId
     * @param map
     * @return
     */
    @RequestMapping(value = "/unbindOpenId",method = RequestMethod.POST)
    public RestResult unbindOpenId(@RequestBody Map map){
        String openId = (String) map.get("open_id");
        return systemCommonService.unbindOpenId(openId);
    }


    /**
     * 获取数据字典的识别类型
     * @param typeCode
     * @return
     */
    @RequestMapping(value = "/findDictByTypeCode",method = RequestMethod.GET)
    public RestResult findDictByTypeCode(String typeCode){
        return systemCommonService.findDictByTypeCode(typeCode);
    }

}
