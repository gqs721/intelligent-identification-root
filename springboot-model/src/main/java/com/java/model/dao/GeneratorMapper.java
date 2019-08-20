package com.java.model.dao;

import com.java.model.domain.Generator;

public interface GeneratorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Generator record);

    int insertSelective(Generator record);
}