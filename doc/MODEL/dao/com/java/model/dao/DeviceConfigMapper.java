package com.java.model.dao;

import com.java.model.domain.DeviceConfig;

public interface DeviceConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceConfig record);

    int insertSelective(DeviceConfig record);

    DeviceConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceConfig record);

    int updateByPrimaryKey(DeviceConfig record);
}