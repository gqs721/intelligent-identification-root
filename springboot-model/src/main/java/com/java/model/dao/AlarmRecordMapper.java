package com.java.model.dao;

import com.java.model.domain.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AlarmRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AlarmRecord record);

    int insertSelective(AlarmRecord record);

    AlarmRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AlarmRecord record);

    int updateByPrimaryKey(AlarmRecord record);

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
     * 统计告警类型近几天的数据，单类型查询
     * @param alarmType
     * @param day
     * @return
     */
    List<Map> alarmTypeStatistical(@Param("alarmType") Integer alarmType, @Param("day") Integer day);

    /**
     * 当天告警类型统计
     * @return
     */
    List<Map> nowAlarmTypeStatistical();

    /**
     * 当天所有告警数量
     * @return
     */
    Integer nowAlarmTypeCount();

    /**
     * 通过typeCode查询字典数据
     * @param typeCode
     * @return
     */
    List<DictData> findByTypeCode(String typeCode);

    int savePushConfig(PushConfig pushConfig);

    List<PushConfig> findPushConfig(@Param("deviceId") Integer deviceId, @Param("serverId") Integer serverId);

    int deletePushConfig(Integer id);

    int savePushWeixin(@Param("openId") String openId, @Param("createTime") Date createTime);

    List<String> findPushWeixin();

    int unbindOpenId(String openId);

    /**
     * 通过用户id查询对应的公众号配置信息
     * @param userId
     * @return
     */
    OfficiaAccountsConfig selectByUserId(Integer userId);

    int savePushRecord(@Param("alarmId") Integer alarmId, @Param("pushId") Integer pushId, @Param("createTime") Date createTime);
}