package com.java.model.dao;

import com.java.model.domain.ChannelConfig;

import java.util.List;
import java.util.Map;

public interface ChannelConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChannelConfig record);

    int insertSelective(ChannelConfig record);

    ChannelConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChannelConfig record);

    int updateByPrimaryKey(ChannelConfig record);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteBatch(Integer[] ids);

    /**
     * 通过设备id批量删除 逻辑删除
     * @param ids
     * @return
     */
    int deleteBatchByDeviceId(List<Integer> ids);


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
     * @param nvrId
     * @return
     */
    List<ChannelConfig> findByNvrId(Integer nvrId);

    /**
     * 通过设备id查询通道配置
     * @param id
     * @return
     */
    ChannelConfig findByDeviceId(Integer id);
}