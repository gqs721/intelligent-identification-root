package com.java.model.dao;

import com.java.model.domain.DictType;

public interface DictTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DictType record);

    int insertSelective(DictType record);

    DictType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DictType record);

    int updateByPrimaryKey(DictType record);

    /**
     * 通过typeCode查询字段
     * @param alarm_type
     * @return
     */
//    DictType findByTypeCode(String alarm_type);
}