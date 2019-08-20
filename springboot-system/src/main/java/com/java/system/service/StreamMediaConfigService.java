package com.java.system.service;

import com.java.common.result.RestResult;
import com.java.model.domain.ServerConfig;
import com.java.model.domain.StreamMediaConfig;

import java.util.List;

/**
 * Created by Mr.BH
 */
public interface StreamMediaConfigService {


    /**
     * 添加流媒体配置
     * @param streamMediaConfig
     * @return
     */
    RestResult save(StreamMediaConfig streamMediaConfig);


    /**
     * 删除流媒体配置
     * @param pkIds
     * @return
     */
    RestResult delete(List<Integer> pkIds);


    /**
     * 修改
     * @param streamMediaConfig
     * @return
     */
    RestResult update(StreamMediaConfig streamMediaConfig);


    /**
     * 分页数据
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    RestResult listPage(String keyword, Integer pageNum, Integer pageSize);


    /**
     * 通过流媒体配置id查询
     * @param pkId
     * @return
     */
    RestResult getStreamMediaConfig(Integer pkId);

    /**
     * 通过用户id查询对应的流媒体配置信息
     * @param userId
     * @return
     */
    RestResult findByUserId(Integer userId);
}
