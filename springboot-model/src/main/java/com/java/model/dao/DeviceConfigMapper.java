package com.java.model.dao;

import com.java.model.domain.DeviceConfig;
import com.java.model.domain.DeviceServer;
import com.java.model.domain.ServerConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DeviceConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DeviceConfig record);

    int insertSelective(DeviceConfig record);

    DeviceConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceConfig record);

    int updateByPrimaryKey(DeviceConfig record);

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

    List<DeviceConfig> findByUserId(@Param("userId") Integer userId, @Param("deviceName") String deviceName);

    DeviceConfig findByDeviceNumber(String deviceNumber);

    int addDeviceServer(DeviceServer deviceServer);

    int deleteDeviceServer(Integer deviceId);

    List<DeviceServer> findByDeviceId(Integer deviceId);

    List<DeviceConfig> findAll();
}