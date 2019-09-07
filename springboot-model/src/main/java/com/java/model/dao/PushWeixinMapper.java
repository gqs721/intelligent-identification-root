package com.java.model.dao;

import com.java.model.domain.PushWeixin;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PushWeixinMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PushWeixin record);

    int insertSelective(PushWeixin record);

    PushWeixin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PushWeixin record);

    int updateByPrimaryKey(PushWeixin record);

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
     * 通过识别类型和设备id查询需要推送的微信用户
     * @param pushType
     * @param deviceId
     * @return
     */
    List<PushWeixin> selectByTypeAndDeviceId(@Param("pushType") Integer pushType, @Param("deviceId") Integer deviceId);

    /**
     * 通过openId查询微信用户信息
     * @param openId
     * @return
     */
    PushWeixin selectByOpenId(String openId);

    int deleteByOpenId(String openId);
}