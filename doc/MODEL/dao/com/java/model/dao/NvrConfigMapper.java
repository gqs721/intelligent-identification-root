package com.java.model.dao;

import com.java.model.domain.NvrConfig;

public interface NvrConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NvrConfig record);

    int insertSelective(NvrConfig record);

    NvrConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NvrConfig record);

    int updateByPrimaryKey(NvrConfig record);
}