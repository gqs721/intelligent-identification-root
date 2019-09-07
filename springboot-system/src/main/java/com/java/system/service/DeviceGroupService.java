package com.java.system.service;

import com.java.common.result.RestResult;
import com.java.model.domain.DeviceGroup;

/**
 * Created by Mr.BH on 2018/7/27.
 */
public interface DeviceGroupService {


    /**
     * 添加分组信息
     * @param deviceConfig
     * @return
     */
    RestResult save(DeviceGroup deviceConfig);

    /**
     * 删除
     * @param groupId
     * @return
     */
    RestResult delete(Integer groupId);

    /**
     * 修改分组信息
     * @param deviceConfig
     * @return
     */
    RestResult update(DeviceGroup deviceConfig);


    /**
     * 通过用户id获取对应的分组信息
     * @param userId
     * @return
     */
    RestResult getAllByUserId(Integer userId);

}
