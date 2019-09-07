package com.java.system.service;

import com.java.common.result.RestResult;

/**
 * Created by Mr.BH
 */
public interface ChannelConfigService {

    /**
     * 分页数据
     * @param keyword
     * @param nvrId
     * @param pageNum
     * @param pageSize
     * @return
     */
    RestResult listPage(String keyword, Integer nvrId, Integer pageNum, Integer pageSize);
}
