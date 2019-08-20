package com.java.model.dao;

import com.java.model.domain.AlarmRecord;
import com.java.model.domain.DictData;
import com.java.model.domain.IdentificationLibrary;
import com.java.model.domain.PushRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface IdentificationLibraryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IdentificationLibrary record);

    int insertSelective(IdentificationLibrary record);

    IdentificationLibrary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IdentificationLibrary record);

    int updateByPrimaryKey(IdentificationLibrary record);

    /**
     * 添加告警记录
     * @param record
     * @return
     */
    int insertAlarmRecord(AlarmRecord record);

    /**
     * 通过typeCode查询字典数据
     * @param typeCode
     * @return
     */
    List<DictData> findByTypeCode(String typeCode);

    int savePushRecord(PushRecord pushRecord);

    List<PushRecord> findPushRecord(@Param("deviceId") Integer deviceId, @Param("serverId") Integer serverId);

    int deletePushRecord(Integer id);

    int savePushWeixin(@Param("openId") String openId, @Param("createTime") Date createTime);

    List<String> findPushWeixin();

    int unbindOpenId(String openId);
}