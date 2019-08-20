package com.java.model.dao;

import com.java.model.domain.ServerConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ServerConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ServerConfig record);

    int insertSelective(ServerConfig record);

    ServerConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ServerConfig record);

    int updateByPrimaryKey(ServerConfig record);

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
     * 通过用户id获取服务器配置信息
     * @param userId
     * @return
     */
    List<ServerConfig> findByUserId(Integer userId);

    List<ServerConfig> findByServerIds(@Param("serverIds") List<Integer> serverIds);
}