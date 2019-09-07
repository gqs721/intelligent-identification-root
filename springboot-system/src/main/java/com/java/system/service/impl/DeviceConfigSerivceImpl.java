package com.java.system.service.impl;

import com.java.common.constants.ParamConstant;
import com.java.common.result.PageResult;
import com.java.common.result.RestResult;
import com.java.common.result.ResultUtils;
import com.java.common.utils.DateUtil;
import com.java.common.utils.LiveGBSUtil;
import com.java.common.utils.StringUtil;
import com.java.model.dao.*;
import com.java.model.domain.*;
import com.java.system.redis.JWTRedisDAO;
import com.java.system.service.DeviceConfigService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Mr.BH
 */
@Service
@Transactional
public class DeviceConfigSerivceImpl implements DeviceConfigService {


    @Autowired
    private DeviceConfigMapper deviceConfigMapper;

    @Autowired
    private ServerConfigMapper serverConfigMapper;

    @Autowired
    private StreamMediaConfigMapper streamMediaConfigMapper;

    @Autowired
    private NvrConfigMapper nvrConfigMapper;

    @Autowired
    private ChannelConfigMapper channelConfigMapper;

    @Autowired
    private DictDataMapper dictDataMapper;

    @Autowired
    private DeviceGroupMapper deviceGroupMapper;

    @Autowired
    private JWTRedisDAO jwtRedisDAO;


    @Override
    public RestResult save(DeviceConfig deviceConfig) {
        
        Date currentDate = new Date();

        deviceConfig.setStatus(0);
        deviceConfig.setDelStatus(0);
        deviceConfig.setCreateTime(currentDate);
        StreamMediaConfig smc = streamMediaConfigMapper.selectByPrimaryKey(deviceConfig.getStreamMediaId());
        deviceConfig.setSipServerIp(smc.getSipServerIp());

        String[] serverIds = deviceConfig.getServerIds().split(",");
        List<DictData> ddList = dictDataMapper.findByTypeCode("identification_type");
        String distinguishType = "";
        for (int i = 0; i < serverIds.length; i++){
            ServerConfig sc = serverConfigMapper.selectByPrimaryKey(Integer.parseInt(serverIds[i]));
            for (DictData dd : ddList){
                if(StringUtil.CheckIsEqual(sc.getIdentificationType(), dd.getDictCode())){
                    distinguishType += dd.getDictValue() + ",";
                }
            }
        }
        if(StringUtil.isNotNull(distinguishType)){
            distinguishType = distinguishType.substring(0, distinguishType.length()-1);
        }
        deviceConfig.setDistinguishType(distinguishType);

        // 添加设备配置信息
        deviceConfigMapper.insertSelective(deviceConfig);

        // 添加设备、模型识别服务关系
        for (int i = 0; i < serverIds.length; i++){
            DeviceServer ds = new DeviceServer();
            ds.setDeviceId(deviceConfig.getId());
            ds.setServerId(Integer.parseInt(serverIds[i]));
            // 1：网络探头，2:nvr设备
            if(deviceConfig.getStorageType() == 2){
                ds.setDeviceType(2);
            }else{
                ds.setDeviceType(1);
            }
            ds.setCreateTime(currentDate);
            deviceConfigMapper.addDeviceServer(ds);
        }

        NvrConfig nc = nvrConfigMapper.selectByPrimaryKey(deviceConfig.getNvrId());

        // 如果添加nvr，把该设备添加到对应的nvr通道里
        if(deviceConfig.getStorageType() == 2){
            ChannelConfig cc = new ChannelConfig();
            cc.setNvrId(nc.getId());
            cc.setDeviceId(deviceConfig.getId());
            cc.setSipId(deviceConfig.getStreamMediaId());
            cc.setNvrNumber(nc.getNvrNumber());
            cc.setChannelNumber(deviceConfig.getDeviceChannel());
            cc.setChannelName(deviceConfig.getDeviceName());
            cc.setChannelAccount(deviceConfig.getDeviceAccount());
            cc.setChannelPassword(deviceConfig.getDevicePassword());
            cc.setManufacturer(deviceConfig.getManufacturer());
            cc.setChannelModel(deviceConfig.getDeviceModel());
            cc.setChannelIp(deviceConfig.getVisitAddress());
            cc.setChannelPort(deviceConfig.getVisitPort());
            cc.setResolutionRatio(deviceConfig.getResolutionRatio());
            cc.setCodeRate(deviceConfig.getCodeRate());
            cc.setDistinguishType(deviceConfig.getDistinguishType());
            cc.setSipIp(deviceConfig.getSipServerIp());
            cc.setNvrIp(nc.getNvrIp());
            cc.setCreateTime(currentDate);

            channelConfigMapper.insertSelective(cc);
        }

        return ResultUtils.success("保存成功");
    }

    @Override
    public RestResult delete(List<Integer> pkIds) {
        if(pkIds == null || pkIds.isEmpty()){
            return ResultUtils.error(1,"没有选择需要删除的数据");
        }

        Integer[] ids = new Integer[pkIds.size()];
        List<Integer> idList = new ArrayList<>();
        if (pkIds.size() > 0){
            for (int i = 0; i < pkIds.size(); i++) {
                ids[i] = pkIds.get(i);
                DeviceConfig dc = deviceConfigMapper.selectByPrimaryKey(pkIds.get(i));
                if(dc.getDeviceType() == 2){
                    idList.add(pkIds.get(i));
                }
            }
        }

        // 管理员信息 逻辑删除
        deviceConfigMapper.deleteBatch(ids);

        // 逻辑删除通道配置的数据
        if(!idList.isEmpty()){
            channelConfigMapper.deleteBatchByDeviceId(idList);
        }

        return ResultUtils.success("删除成功");
    }

    @Override
    public RestResult update(DeviceConfig deviceConfig) {

        DeviceConfig dc = deviceConfigMapper.selectByPrimaryKey(deviceConfig.getId());
        boolean flag = false;
        // 判断是否修改了存储类型
        if(deviceConfig.getStorageType() == dc.getStorageType()){
            flag = true;
        }

        Date currentDate = new Date();

        StreamMediaConfig smc = streamMediaConfigMapper.selectByPrimaryKey(deviceConfig.getStreamMediaId());

        deviceConfig.setSipServerIp(smc.getSipServerIp());

        String[] serverIds = deviceConfig.getServerIds().split(",");
        List<DictData> ddList = dictDataMapper.findByTypeCode("identification_type");
        String distinguishType = "";
        for (int i = 0; i < serverIds.length; i++){
            ServerConfig sc = serverConfigMapper.selectByPrimaryKey(Integer.parseInt(serverIds[i]));
            for (DictData dd : ddList){
                if(StringUtil.CheckIsEqual(sc.getIdentificationType(), dd.getDictCode())){
                    distinguishType += dd.getDictValue() + ",";
                }
            }
        }
        if(StringUtil.isNotNull(distinguishType)){
            distinguishType = distinguishType.substring(0, distinguishType.length()-1);
        }
        deviceConfig.setDistinguishType(distinguishType);

        // 更新设备配置信息
        deviceConfigMapper.updateByPrimaryKeySelective(deviceConfig);

        NvrConfig nc = nvrConfigMapper.selectByPrimaryKey(deviceConfig.getNvrId());

        // 先删除设备、识别服务器的关系，再重新添加设备、模型识别服务关系
        deviceConfigMapper.deleteDeviceServer(deviceConfig.getId());
        for (int i = 0; i < serverIds.length; i++){
            DeviceServer ds = new DeviceServer();
            ds.setDeviceId(deviceConfig.getId());
            ds.setServerId(Integer.parseInt(serverIds[i]));
            // 1：网络探头，2:nvr设备
            if(deviceConfig.getStorageType() == 2){
                ds.setDeviceType(2);
            }else{
                ds.setDeviceType(1);
            }
            ds.setCreateTime(currentDate);
            deviceConfigMapper.addDeviceServer(ds);
        }

        if(deviceConfig.getStorageType() == 0 || deviceConfig.getStorageType() == 1) {
            List<Integer> ids = new ArrayList<>();
            ids.add(deviceConfig.getId());
            channelConfigMapper.deleteBatchByDeviceId(ids);
        }else{
            boolean flag1 = false;
            if(flag){
                ChannelConfig cc = channelConfigMapper.findByDeviceId(deviceConfig.getId());
                // 如果没有修改nvr,那通道里的数据也不做修改，不然得删除，重新插入
                if(deviceConfig.getNvrId() != cc.getNvrId()){
                    List<Integer> ids = new ArrayList<>();
                    ids.add(deviceConfig.getId());
                    channelConfigMapper.deleteBatchByDeviceId(ids);
                    flag1 = true;
                }
            }

            if (!flag || flag1) {
                ChannelConfig cc = new ChannelConfig();
                cc.setNvrId(nc.getId());
                cc.setDeviceId(deviceConfig.getId());
                cc.setSipId(deviceConfig.getStreamMediaId());
                cc.setNvrNumber(nc.getNvrNumber());
                cc.setChannelNumber(deviceConfig.getDeviceChannel());
                cc.setChannelName(deviceConfig.getDeviceName());
                cc.setChannelAccount(deviceConfig.getDeviceAccount());
                cc.setChannelPassword(deviceConfig.getDevicePassword());
                cc.setManufacturer(deviceConfig.getManufacturer());
                cc.setChannelModel(deviceConfig.getDeviceModel());
                cc.setChannelIp(deviceConfig.getVisitAddress());
                cc.setChannelPort(deviceConfig.getVisitPort());
                cc.setResolutionRatio(deviceConfig.getResolutionRatio());
                cc.setCodeRate(deviceConfig.getCodeRate());
                cc.setDistinguishType(deviceConfig.getDistinguishType());
                cc.setSipIp(deviceConfig.getSipServerIp());
                cc.setNvrIp(nc.getNvrIp());
                cc.setUpdateTime(currentDate);

                channelConfigMapper.insertSelective(cc);
            }
        }

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

        if(deviceConfig.getDeviceType() == 2){
            ChannelConfig cc = channelConfigMapper.findByDeviceId(deviceConfig.getId());
            cc.setStatus(deviceConfig.getStatus());
            cc.setUpdateTime(DateUtil.getCurrentDate());
            channelConfigMapper.updateByPrimaryKey(cc);
        }

        return ResultUtils.success(deviceConfig);
    }

    @Override
    public RestResult findByUserId(Integer userId, String deviceName) {
        List<Map> dgList = deviceGroupMapper.findCommonByUserId(userId);

        if(dgList.isEmpty()){
            return ResultUtils.error(1,"没有设备列表");
        }
        JSONArray jsonArr = JSONArray.fromObject(dgList);

        dealTree(jsonArr);

        System.out.println(jsonArr.toString());
        return ResultUtils.success(jsonArr);
    }

    public void dealTree(JSONArray jsonArray){
        for (int i = 0; i < jsonArray.size(); i++){
            jsonArray.getJSONObject(i).put("type", "group");
            if(!jsonArray.getJSONObject(i).getJSONArray("children").isEmpty()){
                dealTree(jsonArray.getJSONObject(i).getJSONArray("children"));
            }

            List<DeviceConfig> dcList= deviceConfigMapper.findByGroupId(jsonArray.getJSONObject(i).getInt("id"));
            for (DeviceConfig dc : dcList){
                JSONObject obj = new JSONObject();
                obj.put("id", dc.getId());
                obj.put("userId", dc.getUserId());
                obj.put("parentId", jsonArray.getJSONObject(i).getInt("id")+"_"+jsonArray.getJSONObject(i).getInt("id"));
                obj.put("groupName", dc.getDeviceName());
                obj.put("type", "device");
                obj.put("children", new JSONArray());

                StreamMediaConfig smc = streamMediaConfigMapper.selectByPrimaryKey(dc.getStreamMediaId());
                Map<String, Object> params = new HashMap<>();
                params.put("q", dc.getDeviceNumber());
                String result = LiveGBSUtil.device_list(smc.getSipServerIp(),smc.getSipPort(), params);
                if(StringUtil.isEmpty(result) || !StringUtil.isJson(result)){
                    obj.put("status", "离线");
                    jsonArray.getJSONObject(i).getJSONArray("children").add(obj);
                    continue;
                }
                JSONObject resultObj = JSONObject.fromObject(result);
                if(resultObj.getInt("DeviceCount") == 0){// 没有设备，继续下一个循环
                    obj.put("status", "离线");
                    jsonArray.getJSONObject(i).getJSONArray("children").add(obj);
                    continue;
                }

                JSONObject obj1 = resultObj.getJSONArray("DeviceList").getJSONObject(0);
                if(obj1.getBoolean("Online")){
                    obj.put("status", "在线");
                }else{
                    obj.put("status", "离线");
                }
                jsonArray.getJSONObject(i).getJSONArray("children").add(obj);
            }

            jsonArray.getJSONObject(i).put("id", jsonArray.getJSONObject(i).getInt("id")+"_"+jsonArray.getJSONObject(i).getInt("id"));
        }
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
