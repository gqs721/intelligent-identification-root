package com.java.model.dao;

import com.java.model.domain.AlarmRecord;

public interface AlarmRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AlarmRecord record);

    int insertSelective(AlarmRecord record);

    AlarmRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AlarmRecord record);

    int updateByPrimaryKey(AlarmRecord record);
}