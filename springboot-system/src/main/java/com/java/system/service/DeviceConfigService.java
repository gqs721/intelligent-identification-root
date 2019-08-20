package com.java.system.service;

import com.java.common.result.RestResult;
import com.java.model.domain.DeviceConfig;

import java.util.List;

/**
 * Created by Mr.BH
 */
public interface DeviceConfigService {


    /**
     * 添加设备配置
     * @param deviceConfig
     * @return
     */
    RestResult save(DeviceConfig deviceConfig);


    /**
     * 删除设备配置
     * @param pkIds
     * @return
     */
    RestResult delete(List<Integer> pkIds);


    /**
     * 修改
     * @param deviceConfig
     * @return
     */
    RestResult update(DeviceConfig deviceConfig);


    /**
     * 分页数据
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    RestResult listPage(String keyword, Integer userId, Integer pageNum, Integer pageSize);


    /**
     * 通过设备配置id查询
     * @param pkId
     * @return
     */
    RestResult getDeviceConfig(Integer pkId);

    /**
     * 禁用或启用设备
     * @param id
     * @return
     */
    RestResult setStatus(Integer id);

    /**
     * 获取公司下的所有设备配置信息
     * @param userId
     * @param deviceName
     * @return
     */
    RestResult findByUserId(Integer userId, String deviceName);

    /**
     * 开始直播
     * @param deviceId
     * @return
     */
    RestResult streamStart(Integer deviceId);

    /**
     * 停止直播
     * @param deviceId
     * @return
     */
    RestResult streamStop(Integer deviceId);
}
