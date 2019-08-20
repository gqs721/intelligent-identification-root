package com.java.model.dao;

import com.java.model.domain.IdentificationLibrary;

public interface IdentificationLibraryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IdentificationLibrary record);

    int insertSelective(IdentificationLibrary record);

    IdentificationLibrary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IdentificationLibrary record);

    int updateByPrimaryKey(IdentificationLibrary record);
}