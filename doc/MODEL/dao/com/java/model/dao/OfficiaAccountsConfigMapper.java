package com.java.model.dao;

import com.java.model.domain.OfficiaAccountsConfig;

public interface OfficiaAccountsConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OfficiaAccountsConfig record);

    int insertSelective(OfficiaAccountsConfig record);

    OfficiaAccountsConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OfficiaAccountsConfig record);

    int updateByPrimaryKey(OfficiaAccountsConfig record);
}