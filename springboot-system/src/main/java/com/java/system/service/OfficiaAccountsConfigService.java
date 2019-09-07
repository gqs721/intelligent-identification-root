package com.java.system.service;

import com.java.common.result.RestResult;
import com.java.model.domain.OfficiaAccountsConfig;

/**
 * Created by Mr.BH
 */
public interface OfficiaAccountsConfigService {


    /**
     * 添加微信公众号配置信息
     * @param officiaAccountsConfig
     * @return
     */
    RestResult save(OfficiaAccountsConfig officiaAccountsConfig);


    /**
     * 修改
     * @param officiaAccountsConfig
     * @return
     */
    RestResult update(OfficiaAccountsConfig officiaAccountsConfig);


    /**
     * 通过用户id查询对应的微信公众号配置信息
     * @param userId
     * @return
     */
    RestResult selectByUserId(Integer userId);
}
