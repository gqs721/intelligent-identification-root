package com.java.system.service;

import com.java.common.result.RestResult;
import com.java.model.domain.ServerConfig;

import java.util.List;

/**
 * Created by Mr.BH
 */
public interface ServerConfigService {


    /**
     * 添加服务器配置
     * @param serverConfig
     * @return
     */
    RestResult save(ServerConfig serverConfig);


    /**
     * 删除服务器配置
     * @param pkIds
     * @return
     */
    RestResult delete(List<Integer> pkIds);


    /**
     * 修改
     * @param serverConfig
     * @return
     */
    RestResult update(ServerConfig serverConfig);


    /**
     * 分页数据
     * @param keyword
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    RestResult listPage(String keyword, Integer userId, Integer pageNum, Integer pageSize);


    /**
     * 通过服务器配置id查询
     * @param pkId
     * @return
     */
    RestResult getServerConfig(Integer pkId);

    /**
     * 通过用户id查询对应的服务器配置信息
     * @param userId
     * @return
     */
    RestResult findByUserId(Integer userId);
}
