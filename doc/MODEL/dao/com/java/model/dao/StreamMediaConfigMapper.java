package com.java.model.dao;

import com.java.model.domain.StreamMediaConfig;

public interface StreamMediaConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StreamMediaConfig record);

    int insertSelective(StreamMediaConfig record);

    StreamMediaConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StreamMediaConfig record);

    int updateByPrimaryKey(StreamMediaConfig record);
}