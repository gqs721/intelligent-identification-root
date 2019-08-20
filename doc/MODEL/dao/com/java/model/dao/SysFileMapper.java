package com.java.model.dao;

import com.java.model.domain.sysFile;

public interface sysFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(sysFile record);

    int insertSelective(sysFile record);

    sysFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(sysFile record);

    int updateByPrimaryKey(sysFile record);
}