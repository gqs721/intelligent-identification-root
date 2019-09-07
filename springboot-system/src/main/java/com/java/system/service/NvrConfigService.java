package com.java.system.service;

import com.java.common.result.RestResult;
import com.java.model.domain.NvrConfig;

import java.util.List;

/**
 * Created by Mr.BH
 */
public interface NvrConfigService {


    /**
     * 添加NVR配置
     * @param nvrConfig
     * @return
     */
    RestResult save(NvrConfig nvrConfig);


    /**
     * 删除NVR配置
     * @param pkIds
     * @return
     */
    RestResult delete(List<Integer> pkIds);


    /**
     * 修改
     * @param nvrConfig
     * @return
     */
    RestResult update(NvrConfig nvrConfig);


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
     * 通过NVR配置id查询
     * @param pkId
     * @return
     */
    RestResult getNvrConfig(Integer pkId);

    /**
     * 通过用户id查询对应的NVR配置信息
     * @param userId
     * @return
     */
    RestResult findByUserId(Integer userId);

    /**
     * 修改NVR配置状态（禁用/启用）
     * @param id
     * @return
     */
    RestResult setStatus(Integer id);
}
