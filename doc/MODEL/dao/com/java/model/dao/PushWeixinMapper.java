package com.java.model.dao;

import com.java.model.domain.PushWeixin;

public interface PushWeixinMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PushWeixin record);

    int insertSelective(PushWeixin record);

    PushWeixin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PushWeixin record);

    int updateByPrimaryKey(PushWeixin record);
}