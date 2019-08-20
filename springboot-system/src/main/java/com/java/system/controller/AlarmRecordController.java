package com.java.system.controller;

import com.java.common.result.RestResult;
import com.java.model.domain.AlarmRecord;
import com.java.system.service.AlarmRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * Created by Mr.BH
 */
@RestController
@RequestMapping("/alarm/record")
@Api(tags = "告警记录接口API", description="告警记录管理")
public class AlarmRecordController {

    @Autowired
    private AlarmRecordService alarmRecordService;


    /**
     * 添加告警记录
     * @param alarmRecord
     * @return
     */
    @ApiOperation(value = "添加告警记录接口", notes = "添加告警记录")
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public RestResult save(AlarmRecord alarmRecord){
        return alarmRecordService.save(alarmRecord);
    }


    /**
     * 删除告警记录
     * @param map
     * @return
     */
    @ApiOperation(value = "删除、批量删除接口", notes = "删除、批量删除，json参数：ids：数组")
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public RestResult delete(@RequestBody Map map){
        List<Integer> ids = (List<Integer>) map.get("ids");
        return alarmRecordService.delete(ids);
    }


    /**
     * 告警记录分页数据
     * @param map
     * @return
     */
    @ApiOperation(value = "获取告警记录列表接口", notes = "获取告警记录列表，json串参数：alarmType：整型，deviceId：整型，userId:整型，beginDate：开始日期，endDate：结束日期，pageNum:页码，pageSize：条数")
    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
    public RestResult listPage(@RequestBody Map map){
        Integer alarmType = (Integer) map.get("alarmType");
        Integer deviceId = (Integer) map.get("deviceId");
        Integer userId = (Integer) map.get("userId");
        String beginDate = (String) map.get("beginDate");
        String endDate = (String) map.get("endDate");
        Integer pageNum = (Integer) map.get("pageNum");
        Integer pageSize = (Integer) map.get("pageSize");
        return alarmRecordService.listPage(alarmType, deviceId, userId, beginDate, endDate, pageNum, pageSize);
    }


    /**
     * 统计告警类型近几天的数据
     * @return
     */
    @ApiOperation(value = "统计告警类型近几天的数据接口", notes = "统计告警类型近几天的数据")
    @RequestMapping(value = "/alarmTypeStatistical",method = RequestMethod.GET)
    public RestResult alarmTypeStatistical(@RequestParam @ApiParam(name="day",value="天数")Integer day){
        return alarmRecordService.alarmTypeStatistical(day);
    }


    /**
     * 统计当天告警记录
     * @return
     */
    @ApiOperation(value = "统计当天告警记录接口", notes = "统计当天告警记录")
    @RequestMapping(value = "/nowAlarmTypeStatistical",method = RequestMethod.GET)
    public RestResult nowAlarmTypeStatistical(){
        return alarmRecordService.nowAlarmTypeStatistical();
    }

}
