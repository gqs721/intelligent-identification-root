package com.java.model.dao;

import com.java.model.domain.ChannelConfig;

public interface ChannelConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelConfig record);

    int insertSelective(ChannelConfig record);

    ChannelConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelConfig record);

    int updateByPrimaryKey(ChannelConfig record);
}