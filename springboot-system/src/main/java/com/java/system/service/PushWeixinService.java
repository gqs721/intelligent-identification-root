package com.java.system.service;

import com.java.common.result.RestResult;
import com.java.model.domain.PushWeixin;

import java.util.List;

/**
 * Created by Mr.BH
 */
public interface PushWeixinService {


    /**
     * 添加微信推送用户信息
     * @param pushWeixin
     * @return
     */
    RestResult save(PushWeixin pushWeixin);


    /**
     * 删除微信推送用户信息
     * @param pkIds
     * @return
     */
    RestResult delete(List<Integer> pkIds);


    /**
     * 修改
     * @param pushWeixin
     * @return
     */
    RestResult update(PushWeixin pushWeixin);


    /**
     * 分页数据
     * @param keyword
     * @param auditStatus
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    RestResult listPage(String keyword, Integer auditStatus, Integer userId, Integer pageNum, Integer pageSize);


    /**
     * 通过openid查询
     * @param openId
     * @return
     */
    RestResult selectByOpenId(String openId);

    /**
     * 修改微信推送用户状态（禁用/启用）
     * @param id
     * @return
     */
    RestResult setStatus(Integer id);

    /**
     * 设置是否管理员
     * @param id
     * @return
     */
    RestResult setAdminStatus(Integer id, Integer adminStatus);

    /**
     * 审核微信用户
     * @param id
     * @return
     */
    RestResult setAuditStatus(Integer id, Integer auditStatus);

    /**
     * 微信端开启/关闭告警推送
     * @param id
     * @return
     */
    RestResult wxSetStatus(Integer id);

    /**
     *
     * 微信端通过openId删除个人信息
     * @param openId
     * @return
     */
    RestResult deleteByOpenId(String openId);
}
