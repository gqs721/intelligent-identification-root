package com.java.model.dao;

import com.java.model.domain.DictData;

import java.util.List;

public interface DictDataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DictData record);

    int insertSelective(DictData record);

    DictData selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DictData record);

    int updateByPrimaryKey(DictData record);

    /**
     * 通过dictCode查询字典数据
     * @param alarm_type
     * @return
     */
    List<DictData> findByTypeCode(String alarm_type);
}