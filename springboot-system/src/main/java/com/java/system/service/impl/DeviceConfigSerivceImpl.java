package com.java.system.service.impl;

import com.java.common.constants.ParamConstant;
import com.java.common.result.PageResult;
import com.java.common.result.RestResult;
import com.java.common.result.ResultUtils;
import com.java.common.utils.LiveGBSUtil;
import com.java.common.utils.StringUtil;
import com.java.model.dao.AdminMapper;
import com.java.model.dao.DeviceConfigMapper;
import com.java.model.dao.StreamMediaConfigMapper;
import com.java.model.domain.DeviceConfig;
import com.java.model.domain.StreamMediaConfig;
import com.java.system.redis.JWTRedisDAO;
import com.java.system.service.DeviceConfigService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mr.BH
 */
@Service
@Transactional
public class DeviceConfigSerivceImpl implements DeviceConfigService {


    @Autowired
    private DeviceConfigMapper deviceConfigMapper;

    @Autowired
    private StreamMediaConfigMapper streamMediaConfigMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private JWTRedisDAO jwtRedisDAO;


    @Override
    public RestResult save(DeviceConfig deviceConfig) {
        
        Date currentDate = new Date();

        deviceConfig.setStatus(0);
        deviceConfig.setDelStatus(0);
        deviceConfig.setCreateTime(currentDate);
        deviceConfigMapper.insertSelective(deviceConfig);

        return ResultUtils.success("保存成功");
    }

    @Override
    public RestResult delete(List<Integer> pkIds) {
        if(pkIds == null || pkIds.isEmpty()){
            return ResultUtils.error(1,"没有选择需要删除的数据");
        }

        Integer[] ids = new Integer[pkIds.size()];

        if (pkIds.size() > 0){
            for (int i = 0; i < pkIds.size(); i++) {
                ids[i] = pkIds.get(i);
            }
        }

        // 管理员信息 逻辑删除
        deviceConfigMapper.deleteBatch(ids);
        return ResultUtils.success("删除成功");
    }

    @Override
    public RestResult update(DeviceConfig deviceConfig) {

        deviceConfigMapper.updateByPrimaryKeySelective(deviceConfig);

        return ResultUtils.success("更新成功");
    }

    @Override
    public RestResult getDeviceConfig(Integer serverId) {

        return ResultUtils.success(deviceConfigMapper.selectByPrimaryKey(serverId));
    }

    @Override
    public RestResult listPage(String keyword, Integer userId, Integer pageNum, Integer pageSize) {

        int n = pageNum == 1 ? pageNum = 0 : (pageNum = (pageNum - 1) * pageSize);

        // 查询参数
        Map domainMap = new HashMap();
        domainMap.put(ParamConstant.KEY_WORD,keyword);
        domainMap.put("userId",userId);
        domainMap.put(ParamConstant.PAGE_SIZE,pageSize);
        domainMap.put(ParamConstant.PAGE_NUM,pageNum);

        List<Map> serverConfigs = deviceConfigMapper.listPage(domainMap);
        int count = deviceConfigMapper.countPage(domainMap);

        return ResultUtils.success(new PageResult<Map>(serverConfigs,count));
    }

    @Override
    public RestResult setStatus(Integer pkId){
        DeviceConfig deviceConfig = deviceConfigMapper.selectByPrimaryKey(pkId);
        if(deviceConfig.getStatus() == 0){// 设置禁用
            deviceConfig.setStatus(1);
        }else{ //设置启用
            deviceConfig.setStatus(0);
        }
        deviceConfigMapper.updateByPrimaryKeySelective(deviceConfig);

        return ResultUtils.success(deviceConfig);
    }

    @Override
    public RestResult findByUserId(Integer userId, String deviceName) {
        List<DeviceConfig> dcList = deviceConfigMapper.findByUserId(userId, deviceName);

        for (DeviceConfig dc: dcList){
            StreamMediaConfig smc = streamMediaConfigMapper.selectByPrimaryKey(dc.getStreamMediaId());
            Map<String, Object> params = new HashMap<>();
            params.put("q", dc.getDeviceNumber());
            String result = LiveGBSUtil.device_list(smc.getSipServerIp(),smc.getSipPort(), params);
            if(StringUtil.isEmpty(result) || !StringUtil.isJson(result)){
                dc.setStatusStr("离线");
                continue;
            }
            JSONObject resultObj = JSONObject.fromObject(result);
            if(resultObj.getInt("DeviceCount") == 0){// 没有设备，继续下一个循环
                dc.setStatusStr("离线");
                continue;
            }

            JSONObject obj = resultObj.getJSONArray("DeviceList").getJSONObject(0);
            if(obj.getBoolean("Online")){
                dc.setStatusStr("在线");
            }else{
                dc.setStatusStr("离线");
            }
        }

        return ResultUtils.success(dcList);
    }

    @Override
    public RestResult streamStart(Integer deviceId) {
        DeviceConfig deviceConfig = deviceConfigMapper.selectByPrimaryKey(deviceId);
        StreamMediaConfig smc = streamMediaConfigMapper.selectByPrimaryKey(deviceConfig.getStreamMediaId());
        Map<String, Object> params = new HashMap<>();
        params.put("serial", deviceConfig.getDeviceNumber());
//        params.put("serial", "34020000001180000001");
//        params.put("code", "34020000001320000001");
//        params.put("starttime", "2019-08-19T13:30:00");
//        params.put("endtime", "2019-08-19T13:31:00");
        String result = LiveGBSUtil.stream_start(smc.getSipServerIp(),smc.getSipPort(), params);
//        String result = LiveGBSUtil.playback_start(smc.getSipServerIp(),smc.getSipPort(), params);
        if(StringUtil.isEmpty(result)){
            return ResultUtils.error(1,"设备离线或请求失败，请刷新页面重试");
        }
        if(StringUtil.CheckIsEqual("device[" + deviceConfig.getDeviceNumber() + "] not found", result)){
            return ResultUtils.error(1,result);
        }
        JSONObject resultObj = JSONObject.fromObject(result);

        return ResultUtils.success(resultObj);
    }

    @Override
    public RestResult streamStop(Integer deviceId) {
        DeviceConfig deviceConfig = deviceConfigMapper.selectByPrimaryKey(deviceId);
        StreamMediaConfig smc = streamMediaConfigMapper.selectByPrimaryKey(deviceConfig.getStreamMediaId());
        Map<String, Object> params = new HashMap<>();
        params.put("serial", deviceConfig.getDeviceNumber());
        String result = LiveGBSUtil.stream_stop(smc.getSipServerIp(),smc.getSipPort(), params);
        if(StringUtil.isEmpty(result)){
            return ResultUtils.error(1,"设备离线或请求失败，请刷新页面重试");
        }
        if(StringUtil.CheckIsEqual("device[" + deviceConfig.getDeviceNumber() + "] not found", result)){
            return ResultUtils.error(1,result);
        }

        return ResultUtils.success(result);
    }

}
