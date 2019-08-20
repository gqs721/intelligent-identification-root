package com.java.model.dao;

import com.java.model.domain.ServerConfig;

public interface ServerConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServerConfig record);

    int insertSelective(ServerConfig record);

    ServerConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServerConfig record);

    int updateByPrimaryKey(ServerConfig record);
}