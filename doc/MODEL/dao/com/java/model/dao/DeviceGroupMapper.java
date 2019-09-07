package com.java.model.dao;

import com.java.model.domain.DeviceGroup;

public interface DeviceGroupMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceGroup record);

    int insertSelective(DeviceGroup record);

    DeviceGroup selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceGroup record);

    int updateByPrimaryKey(DeviceGroup record);
}