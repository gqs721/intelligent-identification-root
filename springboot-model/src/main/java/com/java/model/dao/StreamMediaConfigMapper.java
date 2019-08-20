package com.java.model.dao;

import com.java.model.domain.StreamMediaConfig;

import java.util.List;
import java.util.Map;

public interface StreamMediaConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StreamMediaConfig record);

    int insertSelective(StreamMediaConfig record);

    StreamMediaConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StreamMediaConfig record);

    int updateByPrimaryKey(StreamMediaConfig record);

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
     * 通过用户id获取流媒体配置信息
     * @param userId
     * @return
     */
    List<StreamMediaConfig> findByUserId(Integer userId);

    /**
     * 查询所有的流媒体配置
     * @return
     */
    List<StreamMediaConfig> findAll();
}