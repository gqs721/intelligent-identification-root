package com.java.quartz.job;

import com.java.common.utils.LiveGBSUtil;
import com.java.common.utils.StringUtil;
import com.java.model.dao.*;
import com.java.model.domain.ChannelConfig;
import com.java.model.domain.DeviceConfig;
import com.java.model.domain.DeviceServer;
import com.java.model.domain.StreamMediaConfig;
import com.java.quartz.utils.OpencvUtil;
import com.java.quartz.utils.Test;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private NvrConfigMapper nvrConfigMapper;

    @Autowired
    private ChannelConfigMapper channelConfigMapper;

    @Autowired
    private AlarmRecordMapper alarmRecordMapper;

    @Autowired
    private PushWeixinMapper pushWeixinMapper;

    public final static RestTemplate restTemplate = new RestTemplate();

    // 模型识别线程的线程池
    public final static ExecutorService executor = Executors.newCachedThreadPool();

    @Value("${config.push_weixin_url}")
    private String pushWeixinUrl;

    @Value("${config.face_kind}")
    private Integer faceKind;

    @Value("${config.send_weixin_msg}")
    private boolean sendWeixinMsg;

    // 是否已经启动拉流的list列表
//    public static List<String> deviceList = new ArrayList<>();

    // 存放每个设备启动拉流的FFmpegFrameGrabber
    public static Map<String, Object> deviceMap = new HashMap<>();

    public CollectJob() {}
    /**
     * 正式项目使用
     */
//    public void execute(JobExecutionContext context) {
//        log.info("定时任务 Job 执行开始时间: " + new Date());
//        // 定时网络探头进行拉流
//        initTimedTask();
//        // 定时nvr设备进行拉流
//        initTimedTask1();
//        log.info("定时任务 Job 执行结束时间: " + new Date());
//    }

    /**
     * 测试，展厅使用
     * @param context
     */
    public void execute(JobExecutionContext context) {
        log.info("定时任务 Job 执行开始时间: " + new Date());
        // 进行拉流截图
        initTimedTask3();
        // 进行推流
        initTimedTask2();
        log.info("定时任务 Job 执行结束时间: " + new Date());
    }

    boolean isFinish = true;
    // 视频文件的推流
    public void initTimedTask2(){
        if(isFinish) {
            isFinish = false;
            List<File> fileList = new ArrayList<>();
            File file = new File("D:\\JAVA\\tomcat-8.5.43\\webapps\\video");
            // 如果这个路径是文件夹
            if (file.isDirectory()) {
                // 获取路径下的所有文件
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    // 如果还是文件夹 递归获取里面的文件 文件夹
                    fileList.add(files[i]);
                }
            } else {
                System.out.println("文件：" + file.getPath());
            }

            String outputFile = "rtmp://192.168.1.240:1935/live/222";

            for (int i = 0; i < fileList.size(); i++) {
                try {
                    // 进行推流
                    Test.recordPush4(fileList.get(i), outputFile, 22);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            isFinish = true;
        }
    }

    public void initTimedTask3(){

        DeviceConfig dc = deviceConfigMapper.selectByPrimaryKey(1);
        JSONObject json = new JSONObject();
        json.put("pushWeixinUrl", pushWeixinUrl);
        json.put("faceKind", faceKind);
        json.put("sendWeixinMsg", sendWeixinMsg);

        // 是否已经开始拉流
        boolean isBegin = false;

        for (Map.Entry<String, Object> entry : deviceMap.entrySet()) {
            if (StringUtil.CheckIsEqual(dc.getDeviceNumber()+"_123", entry.getKey())) {
                isBegin = true;
                break;
            }
        }
        if (isBegin) {
            return;
        }

        // 进行拉流
        OpencvUtil op = new OpencvUtil();
        op.executeJavacv(json, "rtmp://192.168.1.240:1935/live/222", dc, "123", alarmRecordMapper, deviceConfigMapper, serverConfigMapper, pushWeixinMapper);
    }

    /**
     * 处理不包含nvr设备的拉流处理
     */
    public void initTimedTask(){

        try {
            JSONObject json = new JSONObject();
            json.put("pushWeixinUrl", pushWeixinUrl);
            json.put("faceKind", faceKind);
            json.put("sendWeixinMsg", sendWeixinMsg);

            // 查询除nvr外的所有设备
            List<DeviceConfig> dcList = deviceConfigMapper.findNoNvrDevice();

            // 如果设备已经禁用或删除，把已经开起来的流进行关闭
            for (Map.Entry<String, Object> entry : deviceMap.entrySet()) {
                boolean flag = true;
                for (int i = 0; i < dcList.size(); i++){
                    String key = dcList.get(i).getDeviceNumber()+"_"+dcList.get(i).getDeviceChannel();
                    if(StringUtil.CheckIsEqual(entry.getKey(), key)){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    if(entry.getValue() != null){
                        FFmpegFrameGrabber grabber = (FFmpegFrameGrabber) entry.getValue();
                        grabber.stop();
                        grabber.release();
                        deviceMap.remove(entry.getKey());
                    }
                }
            }

            for (int i = 0; i < dcList.size(); i++) {
                DeviceConfig dc = dcList.get(i);

                // 是否已经开始拉流
                boolean isBegin = false;

//                for (int j = 0; j < deviceList.size(); j++) {
                for (Map.Entry<String, Object> entry : deviceMap.entrySet()) {
                    if (StringUtil.CheckIsEqual(dc.getDeviceNumber()+"_"+dc.getDeviceChannel(), entry.getKey())) {
                        isBegin = true;
                        break;
                    }
                }
                if (isBegin) {
                    continue;
                }

                // 获取设备对应的流媒体服务器信息
                StreamMediaConfig smc = streamMediaConfigMapper.selectByPrimaryKey(dc.getStreamMediaId());


                // 通过设备id获取所有的模型服务器配置信息
                List<DeviceServer> dsList = deviceConfigMapper.findByDeviceId(dc.getId());
                List<Integer> serverIds = new ArrayList<>();
                for (int j = 0; j < dsList.size(); j++) {
                    serverIds.add(dsList.get(j).getServerId());
                }
                if (serverIds.isEmpty()) {
                    continue;
                }
//                List<ServerConfig> scList = serverConfigMapper.findByServerIds(serverIds);

                // 查询当前设备是否在线,一个设备可能有多个通道
                Map<String, Object> params = new HashMap<>();
                params.put("q", dc.getDeviceNumber());
                String result = LiveGBSUtil.device_list(smc.getSipServerIp(),smc.getSipPort(), params);
//                params.put("serial", dc.getDeviceNumber());
//                String result = LiveGBSUtil.device_channellist(smc.getSipServerIp(), smc.getSipPort(), params);

                if (StringUtil.isEmpty(result) || !StringUtil.isJson(result)) {
                    continue;
                }
                JSONObject resultObj = JSONObject.fromObject(result);
                // 离线或没有设备，继续下一个循环
//                if (!resultObj.getBoolean("Online") || resultObj.getInt("ChannelCount") == 0) {
//                    continue;
//                }
                JSONObject obj = resultObj.getJSONArray("DeviceList").getJSONObject(0);
                if(!obj.getBoolean("Online")){
                    continue;
                }

                // 多通道循环启动拉流
//                JSONArray arr = resultObj.getJSONArray("ChannelList");
//                for (int j = 0; j < arr.size(); j++) {
//                    JSONObject jsonObj = arr.getJSONObject(j);
//                    // 如果服务器没有配置该通道信息，就不进行拉流截图
//                    if(!StringUtil.CheckIsEqual(jsonObj.getString("ID"), dc.getDeviceChannel())){
//                        continue;
//                    }
//                    if (StringUtil.CheckIsEqual("OFF", jsonObj.getString("Status"))) {
//                        continue;
//                    }

                    // 无论是否在直播，都重新开始直播
                    Map<String, Object> params1 = new HashMap<>();
                    params1.put("code", dc.getDeviceChannel());
                    params1.put("serial", dc.getDeviceNumber());
                    String result1 = LiveGBSUtil.stream_start(smc.getSipServerIp(), smc.getSipPort(), params1);
                    if (StringUtil.CheckIsEqual("device[" + dc.getDeviceNumber() + "] not found", result1)) {
                        System.out.println(result1);
                        continue;
                    }
                    if (StringUtil.isEmpty(result1) || !StringUtil.isJson(result1)) {
                        continue;
                    }
                    JSONObject resultObj1 = JSONObject.fromObject(result1);

                    // 进行拉流
                    OpencvUtil op = new OpencvUtil();
                    op.executeJavacv(json, resultObj1.getString("RTSP"), dc, dc.getDeviceChannel(), alarmRecordMapper, deviceConfigMapper, serverConfigMapper, pushWeixinMapper);

                    System.out.println("===========================" + dc.getDeviceNumber() + "_" + dc.getDeviceChannel() + "：启动拉流===========================");
                    System.out.println(resultObj1.toString());
//                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 专门处理nvr设备的拉流处理
     */
    public void initTimedTask1(){

        try {
            JSONObject json = new JSONObject();
            json.put("pushWeixinUrl", pushWeixinUrl);
            json.put("faceKind", faceKind);
            json.put("sendWeixinMsg", sendWeixinMsg);

            // 查询所有的nvr设备
//            List<NvrConfig> nvrList = nvrConfigMapper.findNvr();

            // 查询nvr设备
            List<DeviceConfig> dcList = deviceConfigMapper.findNvrDevice();

            for(DeviceConfig dc : dcList){
                ChannelConfig cc = channelConfigMapper.findByDeviceId(dc.getId());
                dc.setDeviceNumber(cc.getNvrNumber());
            }

            // 如果设备已经禁用或删除，把已经开起来的流进行关闭
            for (Map.Entry<String, Object> entry : deviceMap.entrySet()) {
                boolean flag = true;
                for (int i = 0; i < dcList.size(); i++){
                    String key = dcList.get(i).getDeviceNumber()+"_"+dcList.get(i).getDeviceChannel();
                    if(StringUtil.CheckIsEqual(entry.getKey(), key)){
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    if(entry.getValue() != null){
                        FFmpegFrameGrabber grabber = (FFmpegFrameGrabber) entry.getValue();
                        grabber.stop();
                        grabber.release();
                        deviceMap.remove(entry.getKey());
                    }
                }
            }

            for (int i = 0; i < dcList.size(); i++) {
                DeviceConfig dc = dcList.get(i);

                // 是否已经开始拉流
                boolean isBegin = false;

                for (Map.Entry<String, Object> entry : deviceMap.entrySet()) {
                    if (StringUtil.CheckIsEqual(dc.getDeviceNumber()+"_"+dc.getDeviceChannel(), entry.getKey())) {
                        isBegin = true;
                        break;
                    }
                }
                if (isBegin) {
                    continue;
                }

                // 获取设备对应的流媒体服务器信息
                StreamMediaConfig smc = streamMediaConfigMapper.selectByPrimaryKey(dc.getStreamMediaId());


                // 通过设备id获取所有的模型服务器配置信息
                List<DeviceServer> dsList = deviceConfigMapper.findByDeviceId(dc.getId());
                List<Integer> serverIds = new ArrayList<>();
                for (int j = 0; j < dsList.size(); j++) {
                    serverIds.add(dsList.get(j).getServerId());
                }
                if (serverIds.isEmpty()) {
                    continue;
                }
//                List<ServerConfig> scList = serverConfigMapper.findByServerIds(serverIds);

                // 查询当前设备是否在线,一个设备可能有多个通道
                Map<String, Object> params = new HashMap<>();
//                params.put("q", dc.getDeviceNumber());
//                String result = LiveGBSUtil.device_list(smc.getSipServerIp(),smc.getSipPort(), params);
                params.put("serial", dc.getDeviceNumber());
                String result = LiveGBSUtil.device_channellist(smc.getSipServerIp(), smc.getSipPort(), params);

                if (StringUtil.isEmpty(result) || !StringUtil.isJson(result)) {
                    continue;
                }
                JSONObject resultObj = JSONObject.fromObject(result);
                // 离线或没有设备，继续下一个循环
                if (!resultObj.getBoolean("Online") || resultObj.getInt("ChannelCount") == 0) {
                    continue;
                }
//                JSONObject obj = resultObj.getJSONArray("DeviceList").getJSONObject(0);
//                if(!obj.getBoolean("Online")){
//                    continue;
//                }

                // 多通道循环启动拉流
                JSONArray arr = resultObj.getJSONArray("ChannelList");
                for (int j = 0; j < arr.size(); j++) {
                    JSONObject jsonObj = arr.getJSONObject(j);
                    // 如果服务器没有配置该通道信息，就不进行拉流截图
                    if(!StringUtil.CheckIsEqual(jsonObj.getString("ID"), dc.getDeviceChannel())){
                        continue;
                    }
                    if (StringUtil.CheckIsEqual("OFF", jsonObj.getString("Status"))) {
                        continue;
                    }

                    // 无论是否在直播，都重新开始直播
                    Map<String, Object> params1 = new HashMap<>();
                    params1.put("code", dc.getDeviceChannel());
                    params1.put("serial", dc.getDeviceNumber());
                    String result1 = LiveGBSUtil.stream_start(smc.getSipServerIp(), smc.getSipPort(), params1);
                    if (StringUtil.CheckIsEqual("device[" + dc.getDeviceNumber() + "] not found", result1)) {
                        System.out.println(result1);
                        continue;
                    }
                    if (StringUtil.isEmpty(result1) || !StringUtil.isJson(result1)) {
                        continue;
                    }
                    JSONObject resultObj1 = JSONObject.fromObject(result1);

                    // 进行拉流
                    OpencvUtil op = new OpencvUtil();
                    op.executeJavacv(json, resultObj1.getString("RTSP"), dc, dc.getDeviceChannel(), alarmRecordMapper, deviceConfigMapper, serverConfigMapper, pushWeixinMapper);

                    System.out.println("===========================" + dc.getDeviceNumber() + "_" + dc.getDeviceChannel() + "：启动拉流===========================");
                    System.out.println(resultObj1.toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}  