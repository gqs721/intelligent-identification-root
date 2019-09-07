package com.java.system.controller;

import com.java.common.result.RestResult;
import com.java.model.domain.PushWeixin;
import com.java.system.service.PushWeixinService;
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
@RequestMapping("/push/weixin")
@Api(tags = "微信消息推送接口API", description="微信消息推送管理")
public class PushWeixinController {

    @Autowired
    private PushWeixinService pushWeixinService;


    /**
     * 添加微信推送用户信息（微信端使用）
     * @param pushWeixin
     * @return
     */
    @ApiOperation(value = "添加微信推送用户接口（微信端使用）", notes = "添加微信推送用户（微信端使用）")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public RestResult save(PushWeixin pushWeixin){
        return pushWeixinService.save(pushWeixin);
    }


    /**
     * 删除微信推送用户信息
     * @param map
     * @return
     */
    @ApiOperation(value = "删除、批量删除接口", notes = "删除、批量删除，json参数：ids：数组")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public RestResult delete(@RequestBody Map map){
        List<Integer> ids = (List<Integer>) map.get("ids");
        return pushWeixinService.delete(ids);
    }


    /**
     * 微信用户通过openId删除个人信息
     * @param openId
     * @return
     */
    @ApiOperation(value = "微信用户通过openId删除个人信息接口（微信端使用）", notes = "微信用户通过openId删除个人信息（微信端使用）")
    @RequestMapping(value = "/deleteByOpenId",method = RequestMethod.GET)
    public RestResult deleteByOpenId(@RequestParam String openId){
        return pushWeixinService.deleteByOpenId(openId);
    }


    /**
     * 设置推送类型和摄像头
     * @param map
     * @return
     */
    @ApiOperation(value = "推送类型和摄像头接口", notes = "推送类型和摄像头，json参数：ids：数组，pushId:微信推送用户id")
    @RequestMapping(value = "/setPushTypeAndDevice",method = RequestMethod.POST)
    public RestResult setPushTypeAndDevice(@RequestBody Map map){
        List<Integer> ids = (List<Integer>) map.get("ids");
        Integer pushId = (Integer) map.get("pushId");
        // TODO 需要确认数据格式
//        return pushWeixinService.setPushTypeAndDevice(pushId);
        return null;
    }


    /**
     * 修改微信推送用户信息
     * @param pushWeixin
     * @return
     */
    @ApiOperation(value = "修改微信推送用户信息接口（微信端使用）", notes = "修改微信推送用户信息（微信端使用）")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public RestResult update(PushWeixin pushWeixin){
        return pushWeixinService.update(pushWeixin);
    }


    /**
     * 通过openId获取微信推送用户信息
     * @param openId
     * @return
     */
    @ApiOperation(value = "通过openId获取微信推送用户信息接口（微信端使用）", notes = "通过openId获取微信推送用户信息（微信端使用）")
    @RequestMapping(value = "/selectByOpenId",method = RequestMethod.GET)
    public RestResult selectByOpenId( @RequestParam String openId){
        return pushWeixinService.selectByOpenId(openId);
    }


    /**
     * 修改微信推送用户状态
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/setStatus",method = RequestMethod.GET)
    @ApiOperation(value = "修改微信推送用户状态接口（禁用和启用）", notes = "修改微信推送用户状态（禁用和启用）")
    public RestResult setStatus(@RequestParam Integer id){
        return pushWeixinService.setStatus(id);
    }


    /**
     * 设置是否管理员
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/setAdminStatus",method = RequestMethod.GET)
    @ApiOperation(value = "设置是否管理员状态接口", notes = "设置是否管理员状态")
    public RestResult setAdminStatus(@RequestParam Integer id, @RequestParam Integer adminStatus){
        return pushWeixinService.setAdminStatus(id, adminStatus);
    }


    /**
     * 审核微信推送用户状态
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/setAuditStatus",method = RequestMethod.GET)
    @ApiOperation(value = "审核微信推送用户状态接口", notes = "审核微信推送用户状态")
    public RestResult setAuditStatus(@RequestParam Integer id, @RequestParam Integer auditStatus){
        return pushWeixinService.setAuditStatus(id, auditStatus);
    }


    /**
     * 微信端开启/暂停告警推送
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/wxSetStatus",method = RequestMethod.GET)
    @ApiOperation(value = "开启/暂停告警推送接口（微信端使用）", notes = "开启/暂停告警推送（微信端使用）")
    public RestResult wxSetStatus(@RequestParam Integer id){
        return pushWeixinService.wxSetStatus(id);
    }


    /**
     * 微信推送用户分页数据
     * @param map
     * @return
     */
    @ApiOperation(value = "获取微信推送用户列表接口", notes = "获取微信推送用户列表，json串参数：keyword：关键字，auditStatus：审核状态（全部是-1），userId：整型，pageNum:页码，pageSize：条数")
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public RestResult listPage(@RequestBody Map map){
        String keyword = (String) map.get("keyword");
        Integer auditStatus = (Integer) map.get("auditStatus");
        Integer userId = (Integer) map.get("userId");
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");
        return pushWeixinService.listPage(keyword, auditStatus, userId, pageNum, pageSize);
    }

}
