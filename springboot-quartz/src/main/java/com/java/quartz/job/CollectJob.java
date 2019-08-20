package com.java.quartz.job;

import com.java.common.result.ResultUtils;
import com.java.common.utils.*;
import com.java.model.dao.*;
import com.java.model.domain.*;
import com.java.quartz.utils.OpencvUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;


import java.util.*;
import java.util.List;

/**
 * @author gqs
 * 定时工具类
 */
@Slf4j
public class CollectJob implements BaseJob {

    @Autowired
    private StreamMediaConfigMapper streamMediaConfigMapper;

    @Autowired
    private DeviceConfigMapper deviceConfigMapper;

    @Autowired
    private ServerConfigMapper serverConfigMapper;

    @Autowired
    private AlarmRecordMapper alarmRecordMapper;

    public final static RestTemplate restTemplate = new RestTemplate();

    @Value("${config.push_weixin_url}")
    private String pushWeixinUrl;

    @Value("${config.face_kind}")
    private Integer faceKind;

    public static List<String> deviceList = new ArrayList<>();

    public CollectJob() {}
     
    public void execute(JobExecutionContext context) {
        log.info("定时任务 Job 执行开始时间: " + new Date());
        initTimedTask();
        log.info("定时任务 Job 执行结束时间: " + new Date());
    }

    public void initTimedTask(){

        JSONObject json = new JSONObject();
        json.put("pushWeixinUrl", pushWeixinUrl);
        json.put("faceKind", faceKind);

        List<DeviceConfig> dcList = deviceConfigMapper.findAll();

        for (int i = 0; i < dcList.size(); i++){
            DeviceConfig dc = dcList.get(i);

            // 是否已经开始拉流
            boolean isBegin = false;
            for (int j = 0; j < deviceList.size(); j++){
                if(StringUtil.CheckIsEqual(dc.getDeviceNumber(), deviceList.get(j))){
                    isBegin = true;
                    break;
                }
            }
            if(isBegin){
                continue;
            }

            // 获取设备对应的流媒体服务器信息
            StreamMediaConfig smc = streamMediaConfigMapper.selectByPrimaryKey(dc.getStreamMediaId());


            // 通过设备id获取所有的模型服务器配置信息
            List<DeviceServer> dsList= deviceConfigMapper.findByDeviceId(dc.getId());
            List<Integer> serverIds = new ArrayList<>();
            for (int j = 0; j < dsList.size(); j++){
                serverIds.add(dsList.get(j).getServerId());
            }
            if(serverIds.isEmpty()){
                continue;
            }
            List<ServerConfig> scList = serverConfigMapper.findByServerIds(serverIds);

            // 查询当前设备是否在线,一个设备可能有多个通道
            Map<String, Object> params = new HashMap<>();
//            params.put("q", dc.getDeviceNumber());
//            String result = LiveGBSUtil.device_list(smc.getSipServerIp(),smc.getSipPort(), params);
            params.put("serial", dc.getDeviceNumber());
            String result = LiveGBSUtil.device_channellist(smc.getSipServerIp(),smc.getSipPort(), params);

            if(StringUtil.isEmpty(result) || !StringUtil.isJson(result)){
                continue;
            }
            JSONObject resultObj = JSONObject.fromObject(result);

            if(!resultObj.getBoolean("Online")){
                continue;
            }
            if(resultObj.getInt("ChannelCount") == 0){// 没有设备，继续下一个循环
                continue;
            }
//            JSONObject obj = resultObj.getJSONArray("DeviceList").getJSONObject(0);
//            if(!obj.getBoolean("Online")){
//                continue;
//            }

            JSONArray arr = resultObj.getJSONArray("ChannelList");
            for (int j = 0; j < arr.size(); j++){
                JSONObject jsonObj = arr.getJSONObject(j);
                if(StringUtil.CheckIsEqual("OFF",jsonObj.getString("Status"))){
                    continue;
                }

                // 无论是否在直播，都重新开始直播
                Map<String, Object> params1 = new HashMap<>();
                params1.put("serial", jsonObj.getString("DeviceID"));
                params1.put("code", jsonObj.getString("ID"));
                String result1 = LiveGBSUtil.stream_start(smc.getSipServerIp(),smc.getSipPort(), params1);
                if(StringUtil.CheckIsEqual("device[" + dc.getDeviceNumber() + "] not found", result1)){
                    System.out.println(result1);
                    continue;
                }
                if(StringUtil.isEmpty(result1) || !StringUtil.isJson(result1)){
                    continue;
                }
                JSONObject resultObj1 = JSONObject.fromObject(result1);

                // 进行拉流
                OpencvUtil op = new OpencvUtil();
                op.executeJavacv(json, resultObj1.getString("RTSP"), dc, scList, alarmRecordMapper);

                System.out.println("===========================" + jsonObj.getString("DeviceID") + "_" + jsonObj.getString("ID") + "：启动拉流===========================");
                System.out.println(resultObj1.toString());
            }
        }
    }

}  