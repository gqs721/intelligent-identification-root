package com.java.system.service;

import com.java.common.result.RestResult;
import com.java.model.domain.AlarmRecord;
import com.java.model.domain.DeviceConfig;

import java.util.List;

/**
 * Created by Mr.BH
 */
public interface AlarmRecordService {


    /**
     * 添加告警记录
     * @param alarmRecord
     * @return
     */
    RestResult save(AlarmRecord alarmRecord);


    /**
     * 删除告警记录
     * @param pkIds
     * @return
     */
    RestResult delete(List<Integer> pkIds);


    /**
     * 分页数据
     * @param alarmType
     * @param deviceId
     * @param userId
     * @param beginDate
     * @param endDate
     * @param pageNum
     * @param pageSize
     * @return
     */
    RestResult listPage(Integer alarmType, Integer deviceId, Integer userId, String beginDate, String endDate, Integer pageNum, Integer pageSize);

    /**
     * 统计告警类型近几天的数据
     * @param day
     * @return
     */
    RestResult alarmTypeStatistical(Integer day);

    /**
     * 统计当天告警记录
     * @return
     */
    RestResult nowAlarmTypeStatistical();
}
