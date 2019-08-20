package com.java.model.dao;

import com.java.model.domain.DictData;

public interface DictDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DictData record);

    int insertSelective(DictData record);

    DictData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DictData record);

    int updateByPrimaryKey(DictData record);
}