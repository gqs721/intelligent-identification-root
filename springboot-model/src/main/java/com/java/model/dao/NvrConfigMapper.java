package com.java.model.dao;

import com.java.model.domain.NvrConfig;

import java.util.List;
import java.util.Map;

public interface NvrConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(NvrConfig record);

    int insertSelective(NvrConfig record);

    NvrConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NvrConfig record);

    int updateByPrimaryKey(NvrConfig record);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteBatch(Integer[] ids);


    /**
     * 列表
     * @param queryMap
     * @return
     */
    List<Map> listPage(Map queryMap);

    /**
     * 统计
     * @param queryMap
     * @return
     */
    Integer countPage(Map queryMap);

    /**
     * 通过用户id获取nvr配置信息
     * @param userId
     * @return
     */
    List<NvrConfig> findByUserId(Integer userId);

    List<NvrConfig> findNvr();

    /**
     * 通过nvr编号查询nvr信息
     * @param nvrNumber
     * @return
     */
    NvrConfig findByNvrNumber(String nvrNumber);
}