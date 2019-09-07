package com.java.quartz.utils;


import com.java.common.utils.*;
import com.java.model.dao.AlarmRecordMapper;
import com.java.model.dao.DeviceConfigMapper;
import com.java.model.dao.PushWeixinMapper;
import com.java.model.dao.ServerConfigMapper;
import com.java.model.domain.*;
import com.java.quartz.WebSocketServer;
import com.java.quartz.job.CollectJob;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by fang on 17-4-6.
 * 实现每播放一秒视频就截取一张图片保存到本地的操作
 * 没有使用ffmpeg,则只能读取avi格式视频
 * 下载google javcv.jar
 * sudo add-apt-repository ppa:kirillshkrogalev/ffmpeg-next
 * sudo apt-get update
 * sudo apt-get install ffmpeg
 * /home/fang/BigDataSoft/opencv-2.4.13/3rdparty/ffmpeg中有opencv_ffmpeg_64.dll文件
 * 不能解析视频,提示moov atom not found 是文件有错
 */
@Component
public class OpencvUtil {

    // 拉流和图片处理的线程池
    final ExecutorService executorFixed = Executors.newFixedThreadPool(2);

    // 初始化队列
    final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(10);

    // 人脸识别推送记录
    public static Map<String, Object> nameMap = new HashMap<>();

    /**
     * 启动拉流线程
     * @param grabber
     * @param dc
     * @throws Exception
     */
    public void javacvFun(FFmpegFrameGrabber grabber, String rtspPath, DeviceConfig dc, String deviceChannel, Integer frameInterval, Integer reloadRtsp) {
        executorFixed.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    dealRTSP(grabber, rtspPath, dc, deviceChannel, frameInterval, reloadRtsp);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    CollectJob.deviceMap.remove(dc.getDeviceNumber()+"_"+deviceChannel);
                }
            }
        });
    }

    /**
     * 启动图片处理线程
     * @param deviceChannel
     * @param dc
     * @param alarmRecordMapper
     * @param json
     */
    public void dealIdentificationFun(String deviceChannel, DeviceConfig dc, AlarmRecordMapper alarmRecordMapper, JSONObject json, DeviceConfigMapper deviceConfigMapper, ServerConfigMapper serverConfigMapper, PushWeixinMapper pushWeixinMapper) {
        executorFixed.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    dealIdentification(deviceChannel, dc, alarmRecordMapper, json, deviceConfigMapper, serverConfigMapper, pushWeixinMapper);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 启动模型识别线程
     * @param base64Img
     * @param sc
     * @param dc
     * @param alarmRecordMapper
     * @param json
     */
    public void dealModelFun(String base64Img, ServerConfig sc, DeviceConfig dc, AlarmRecordMapper alarmRecordMapper, JSONObject json, PushWeixinMapper pushWeixinMapper) {
        Future future = CollectJob.executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    dealModel(base64Img, sc, dc, alarmRecordMapper, json, pushWeixinMapper);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        if(future.isDone()){
            future.cancel(true);
        }
    }

    /**
     * 按帧截取图片
     *
     * @param rtspPath -该地址可以是网络直播/录播地址，也可以是远程/本地文件路径
     * @throws Exception
     */
    public void executeJavacv(JSONObject json, String rtspPath, DeviceConfig dc, String deviceChannel, AlarmRecordMapper alarmRecordMapper, DeviceConfigMapper deviceConfigMapper, ServerConfigMapper serverConfigMapper, PushWeixinMapper pushWeixinMapper) {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(rtspPath);
        grabber.setOption("rtsp_transport","tcp");
//        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber("D:\\JAVA\\tomcat-8.5.43\\webapps\\video\\TopEyeVideo_20190626113520.mp4");

        try {
            // 获取截取帧间隔
            List<DictData> list1 = alarmRecordMapper.findByTypeCode("frame_interval");
            // 获取重新拉流间隔
            List<DictData> list2 = alarmRecordMapper.findByTypeCode("reload_rtsp");
            // 拉流线程
            javacvFun(grabber, rtspPath, dc, deviceChannel, Integer.parseInt(list1.get(0).getDictCode()), Integer.parseInt(list2.get(0).getDictCode()));

            // 处理图片线程
            dealIdentificationFun(deviceChannel, dc, alarmRecordMapper, json, deviceConfigMapper, serverConfigMapper, pushWeixinMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dealRTSP(FFmpegFrameGrabber grabber, String rtspPath, DeviceConfig dc, String deviceChannel, Integer frameInterval, Integer reloadRtsp) throws FrameGrabber.Exception {
        try {//建议在线程中使用该方法
            grabber.start();
            CollectJob.deviceMap.put(dc.getDeviceNumber()+"_"+deviceChannel, grabber);
            Frame frame = null;
            int i = 0;
            while (grabber != null && (frame = grabber.grabFrame()) != null) {
//                Thread.sleep(15);
                // 每隔多少帧进行重新拉流，1s是25-30帧
                if(i != 0 && (i % reloadRtsp) == 0){
                    if(grabber != null){
                        grabber.stop();
                        grabber = null;
//                        grabber.restart();
                        System.out.println("=====================重新拉流成功："+rtspPath+"======================");
                        grabber = new FFmpegFrameGrabber(rtspPath);
                        grabber.setOption("rtsp_transport","tcp");
                        grabber.start();
                        i = 0;
                        frame = grabber.grabFrame();
                    }
                }

                // 每隔几帧截取一张图
                if((i % frameInterval) == 0) {
                    BufferedImage in = imageToMat(frame);
                    if(in == null){
                        continue;
                    }
                    // bufferImage->base64
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ImageIO.write(in, "jpeg", outputStream);
                    // base64图片
                    String base64Img = Base64Util.getenBASE64inCodec(outputStream.toByteArray());

                    // 如果队列满了，更新图片，把最早插入的图片移除，插入最新的
                    if(queue.size() == 5){
                        queue.remove();
                        System.out.println("======拉流移除头队列======");
                    }
                    queue.put(base64Img);
                    System.out.println("======加入队列======");
                }
                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
            CollectJob.deviceMap.remove(dc.getDeviceNumber()+"_"+deviceChannel);
        }finally {
            if (grabber != null) {
                grabber.stop();
                grabber.release();
            }
            CollectJob.deviceMap.remove(dc.getDeviceNumber()+"_"+deviceChannel);
        }
    }

    public void dealIdentification(String deviceChannel, DeviceConfig dc, AlarmRecordMapper alarmRecordMapper, JSONObject json, DeviceConfigMapper deviceConfigMapper, ServerConfigMapper serverConfigMapper, PushWeixinMapper pushWeixinMapper) throws InterruptedException {
        String base64Img = "";

        while ((base64Img = queue.take()) != "") {
            System.out.println("======处理图片移除头队列======");
            List<DeviceServer> dsList = deviceConfigMapper.findByDeviceId(dc.getId());
            List<Integer> serverIds = new ArrayList<>();
            for (int j = 0; j < dsList.size(); j++) {
                serverIds.add(dsList.get(j).getServerId());
            }
            if (serverIds.isEmpty()) {
                continue;
            }
            List<ServerConfig> scList = serverConfigMapper.findByServerIds(serverIds);
            for (int j = 0; j < scList.size(); j++) {
                ServerConfig sc = scList.get(j);
                // 开启模型识别线程
                dealModelFun(base64Img, sc, dc, alarmRecordMapper, json, pushWeixinMapper);
            }
        }
    }

    private boolean dealFace = true;
    private boolean dealHardhat = true;
    private boolean dealSmoke = true;
    private boolean dealLadder = true;
    private boolean dealUniform = true;
    private boolean dealPhone = true;
    private boolean dealFire = true;
    private boolean dealInvade = true;

    public void dealModel(String base64Img, ServerConfig sc, DeviceConfig dc, AlarmRecordMapper alarmRecordMapper, JSONObject json, PushWeixinMapper pushWeixinMapper){
        String result = "";
        String ip = sc.getServerIp();
        int port = sc.getServerPort();

        boolean isDealModel = true;
        // TODO 一分钟内只保存一次告警记录
        Integer pushInterval = 0;
        List<PushConfig> pcList = alarmRecordMapper.findPushConfig(dc.getId(), sc.getId());

        if(!StringUtil.CheckIsEqual("6", sc.getIdentificationType())) {
            if (pcList.isEmpty()) {
                PushConfig pc = new PushConfig();
                pc.setDeviceId(dc.getId());
                pc.setServerId(sc.getId());
                pc.setCreateTime(DateUtil.getCurrentDate());
                alarmRecordMapper.savePushConfig(pc);
            } else {
                PushConfig pc = pcList.get(0);
                if (DateUtil.calLastedTime(pc.getCreateTime(), DateUtil.getCurrentDate()) >= pc.getPushInterval()) {

                    for (int k = 0; k < pcList.size(); k++) {
                        alarmRecordMapper.deletePushConfig(pcList.get(k).getId());
                    }

                    pc = new PushConfig();
                    pc.setDeviceId(dc.getId());
                    pc.setServerId(sc.getId());
                    pc.setCreateTime(DateUtil.getCurrentDate());
                    alarmRecordMapper.savePushConfig(pc);
                } else {
                    isDealModel = false;
                }
            }
        }else{
            PushConfig pc = new PushConfig();
            if (pcList.isEmpty()) {
                pc.setDeviceId(dc.getId());
                pc.setServerId(sc.getId());
                pc.setCreateTime(DateUtil.getCurrentDate());
                alarmRecordMapper.savePushConfig(pc);
            } else {
                pc = pcList.get(0);
            }
            pushInterval = pc.getPushInterval();
            // 人脸识别的先不进行限时推送,不推送陌生人的识别记录
            if(json.getInt("faceKind") == 0) {
                isDealModel = false;
            }
        }

        if(isDealModel) {
            if (dealSmoke && StringUtil.CheckIsEqual("1", sc.getIdentificationType())) {// TODO 吸烟识别
                dealSmoke = false;
                result = RecognitionModelUtil.modelByBase64Pic(base64Img, ip, port, dc.getDeviceNumber() + dc.getId(), "smoke");
                dealSmoke = true;
            } else if (dealHardhat && StringUtil.CheckIsEqual("2", sc.getIdentificationType())) { // TODO 火情识别
                dealFire = false;
//            result = RecognitionModelUtil.modelByBase64Pic(base64Img, ip, port, dc.getDeviceNumber() + sc.getId(), "dealFire");
                dealFire = true;
            } else if (dealHardhat && StringUtil.CheckIsEqual("3", sc.getIdentificationType())) { // TODO 区域入侵识别
                dealInvade = false;
//            result = RecognitionModelUtil.modelByBase64Pic(base64Img, ip, port, dc.getDeviceNumber() + sc.getId(), "dealInvade");
                dealInvade = true;
            } else if (dealHardhat && StringUtil.CheckIsEqual("4", sc.getIdentificationType())) { // TODO 安全帽识别
                dealHardhat = false;
                result = RecognitionModelUtil.modelByBase64Pic(base64Img, ip, port, dc.getDeviceNumber() + dc.getId(), "hardhat");
                dealHardhat = true;
            } else if (dealUniform && StringUtil.CheckIsEqual("5", sc.getIdentificationType())) {// TODO 工作服识别
                dealUniform = false;
                result = RecognitionModelUtil.modelByBase64Pic(base64Img, ip, port, dc.getDeviceNumber() + dc.getId(), "uniform");
                dealUniform = true;
            } else if (dealFace && StringUtil.CheckIsEqual("6", sc.getIdentificationType())) { // TODO 黑名单识别/人脸识别
                dealFace = false;
                result = RecognitionModelUtil.faceRecognitionModelByBase64Pic(base64Img, ip, port, dc.getId() + "", json.getInt("faceKind"));
                dealFace = true;
            } else if (dealLadder && StringUtil.CheckIsEqual("7", sc.getIdentificationType())) {// TODO 扶梯子识别
                dealLadder = false;
                result = RecognitionModelUtil.modelByBase64Pic(base64Img, ip, port, dc.getDeviceNumber() + dc.getId(), "ladder");
                dealLadder = true;
            } else if (dealPhone && StringUtil.CheckIsEqual("8", sc.getIdentificationType())) {// TODO 手机识别
                dealPhone = false;
                result = RecognitionModelUtil.modelByBase64Pic(base64Img, ip, port, dc.getDeviceNumber() + dc.getId(), "phone");
                dealPhone = true;
            }

            if (StringUtil.isEmpty(result)) {
                return;
            }

            if (StringUtil.isJson(result)) {

                JSONObject obj = JSONObject.fromObject(result);
                if (obj.getInt("code") == 0) {
                    saveAlarmRecord(sc.getIdentificationType(), result, dc, alarmRecordMapper, json, pushInterval, pushWeixinMapper);
//                    if (!StringUtil.CheckIsEqual("6", sc.getIdentificationType())) {
//                        if (prList.isEmpty()) {
//                            PushRecord pr = new PushRecord();
//                            pr.setDeviceId(dc.getId());
//                            pr.setServerId(sc.getId());
//                            pr.setCreateTime(DateUtil.getCurrentDate());
//                            alarmRecordMapper.savePushRecord(pr);
//
//                            saveAlarmRecord(sc.getIdentificationType(), result, dc, alarmRecordMapper, json, 0);
//                        } else {
//                            PushRecord pr = prList.get(0);
//                            if (DateUtil.calLastedTime(pr.getCreateTime(), DateUtil.getCurrentDate()) >= pr.getPushInterval()) {
//
//                                for (int k = 0; k < prList.size(); k++) {
//                                    alarmRecordMapper.deletePushRecord(prList.get(k).getId());
//                                }
//
//                                pr = new PushRecord();
//                                pr.setDeviceId(dc.getId());
//                                pr.setServerId(sc.getId());
//                                pr.setCreateTime(DateUtil.getCurrentDate());
//                                alarmRecordMapper.savePushRecord(pr);
//
//                                saveAlarmRecord(sc.getIdentificationType(), result, dc, alarmRecordMapper, json, 0);
//                            }
//                        }
//                    } else {
//                        Integer pushInterval = 0;
//                        PushRecord pr = new PushRecord();
//                        if (prList.isEmpty()) {
//                            pr.setDeviceId(dc.getId());
//                            pr.setServerId(sc.getId());
//                            pr.setCreateTime(DateUtil.getCurrentDate());
//                            alarmRecordMapper.savePushRecord(pr);
//                        } else {
//                            pr = prList.get(0);
//                        }
//                        pushInterval = pr.getPushInterval();
//                        // 人脸识别的先不进行限时推送,不推送陌生人的识别记录
//                        if (json.getInt("faceKind") != 0) {
//                            saveAlarmRecord(sc.getIdentificationType(), result, dc, alarmRecordMapper, json, pushInterval);
//                        }
//                    }
                }
            }
        }
    }

    public void saveAlarmRecord(String type, String result, DeviceConfig dc, AlarmRecordMapper alarmRecordMapper, JSONObject json, Integer pushInterval, PushWeixinMapper pushWeixinMapper){
        List<AlarmRecord> arList = new ArrayList<>();

        Date date = DateUtil.getCurrentDate();

        if(StringUtil.CheckIsEqual("1", type) || StringUtil.CheckIsEqual("4", type) || StringUtil.CheckIsEqual("5", type) || StringUtil.CheckIsEqual("7", type) || StringUtil.CheckIsEqual("8", type)){ // 吸烟，安全帽，工作服，扶梯子，手机
            JSONObject obj = JSONObject.fromObject(result);
            if(obj.getInt("code") == 0){
                if(obj.getJSONObject("info") != null){
                    JSONObject infoObj = obj.getJSONObject("info");

                    AlarmRecord ar = new AlarmRecord();
                    ar.setUserId(dc.getUserId());
                    ar.setDeviceId(dc.getId());
                    ar.setDeviceName(dc.getDeviceName());
                    ar.setAlarmDate(date);
                    ar.setAlarmDateStr(DateUtil.parseDate(date));
                    ar.setAlarmTime(DateUtil.getTime());
                    ar.setStatus(0);
                    ar.setDelStatus(0);
                    ar.setCreateTime(date);
                    ar.setAlarmType(Integer.parseInt(type));

                    ar.setPrintscreen(infoObj.getString("ImageUrl"));
                    ar.setImageFile(infoObj.getString("ImageFile"));
                    alarmRecordMapper.insertSelective(ar);
                    arList.add(ar);
                }
            }
        }else if(StringUtil.CheckIsEqual("6", type)){
            JSONObject obj = JSONObject.fromObject(result);
            if(obj.getInt("code") == 0){
                if(obj.getJSONObject("data") != null){
                    JSONObject dataObj = obj.getJSONObject("data");
                    if(!StringUtil.isEmpty(dataObj.getString("List"))){
                        JSONArray array = dataObj.getJSONArray("List");
                        for (int i = 0; i < array.size(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String peopleName = object.getString("PeopleName");
                            AlarmRecord ar = new AlarmRecord();
                            ar.setUserId(dc.getUserId());
                            ar.setDeviceId(dc.getId());
                            ar.setDeviceName(dc.getDeviceName());
                            ar.setPeopleId(object.getInt("PeopleID"));
                            ar.setPeopleKind(object.getInt("PeopleKind"));
                            ar.setPeopleName(peopleName);
                            ar.setAlarmDate(date);
                            ar.setAlarmDateStr(DateUtil.parseDate(date));
                            ar.setAlarmTime(DateUtil.getTime());
                            ar.setStatus(0);
                            ar.setDelStatus(0);
                            ar.setCreateTime(date);
                            ar.setAlarmType(Integer.parseInt(type));

                            ar.setPrintscreen(object.getString("ImageUrl"));
                            ar.setImageFile(object.getString("ImageFile"));
                            ar.setSmallImage(object.getString("SmallImage"));
                            ar.setSmallUrl(object.getString("SmallUrl"));

                            if(StringUtil.isNotNull(peopleName)) {
                                if (nameMap.containsKey(peopleName)) {
                                    Date pushDate = (Date) nameMap.get(peopleName);
                                    if(DateUtil.calLastedTime(pushDate, DateUtil.getCurrentDate()) >= pushInterval){
                                        nameMap.put(peopleName,DateUtil.getCurrentDate());
                                        alarmRecordMapper.insertSelective(ar);
                                        arList.add(ar);
                                    }
                                } else {
                                    nameMap.put(peopleName,DateUtil.getCurrentDate());
                                    alarmRecordMapper.insertSelective(ar);
                                    arList.add(ar);
                                }
                            }
                        }
                    }
                }
            }
        }

        // 通过websocket推送告警记录
        pushWebSocket(arList, dc.getUserId());
        // 通过微信推送告警记录
        if(json.getBoolean("sendWeixinMsg")) {
            pushWeixin(arList, alarmRecordMapper, json.getString("pushWeixinUrl"), pushWeixinMapper);
        }
    }

    // 推送消息到webSocket
    public void pushWebSocket(List<AlarmRecord> arList, Integer userId){
        try {
            if(arList != null && !arList.isEmpty()) {
                WebSocketServer.sendInfo(JSONArray.fromObject(arList).toString(), userId + "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 推送消息到微信
    public void pushWeixin(List<AlarmRecord> arList, AlarmRecordMapper alarmRecordMapper, String pushWeixinUrl, PushWeixinMapper pushWeixinMapper){
//        List<String> openIdList = alarmRecordMapper.findPushWeixin();


        if(arList != null && !arList.isEmpty()) {
            for (int i = 0; i < arList.size(); i++) {
                AlarmRecord ar = arList.get(i);

                // 查询对应要推送的微信用户
                List<PushWeixin> pwList= pushWeixinMapper.selectByTypeAndDeviceId(ar.getAlarmType(), ar.getDeviceId());

                for (int j = 0; j < pwList.size(); j++) {
                    if(StringUtil.isEmpty(pwList.get(j).getOpenId())){
                        continue;
                    }

                    // 保存告警记录和微信用户的关系
                    alarmRecordMapper.savePushRecord(ar.getId(), pwList.get(j).getId(), DateUtil.getCurrentDate());

                    // 先查询对应的公众号配置信息
                    OfficiaAccountsConfig oac = alarmRecordMapper.selectByUserId(pwList.get(j).getUserId());
                    if(oac == null){
                        // 直接查询超级管理员配置的微信公众号信息
                        oac = alarmRecordMapper.selectByUserId(1);
                        if(oac == null){
                            continue;
                        }
                    }

                    JSONObject json = new JSONObject();
                    try {
                        json.put("open_id", pwList.get(j).getOpenId());
                        json.put("warn_time", DateUtil.parseDateStr(ar.getAlarmDate()));
                        json.put("warn_position", ar.getDeviceName());
                        if (ar.getAlarmType() == 1) {
                            json.put("warn_kind", "吸烟告警");
                            json.put("content", "你有一个" + json.getString("warn_kind") + "，请点击查看");
                        } else if (ar.getAlarmType() == 2) {
                            json.put("warn_kind", "火情告警");
                            json.put("content", "你有一个" + json.getString("warn_kind") + "，请点击查看");
                        } else if (ar.getAlarmType() == 3) {
                            json.put("warn_kind", "区域入侵告警");
                            json.put("content", "你有一个" + json.getString("warn_kind") + "，请点击查看");
                        } else if (ar.getAlarmType() == 4) {
                            json.put("warn_kind", "安全帽告警");
                            json.put("content", "你有一个" + json.getString("warn_kind") + "，请点击查看");
                        } else if (ar.getAlarmType() == 5) {
                            json.put("warn_kind", "工作服告警");
                            json.put("content", "你有一个" + json.getString("warn_kind") + "，请点击查看");
                        } else if (ar.getAlarmType() == 6) {
                            json.put("warn_kind", "人员考勤");
                            json.put("content", ar.getPeopleName()+"已签到");
                        } else if (ar.getAlarmType() == 7) {
                            json.put("warn_kind", "扶梯子告警");
                            json.put("content", "你有一个" + json.getString("warn_kind") + "，请点击查看");
                        } else if (ar.getAlarmType() == 8) {
                            json.put("warn_kind", "玩手机告警");
                            json.put("content", "你有一个" + json.getString("warn_kind") + "，请点击查看");
                        }

//                        json.put("content", "你有一个" + json.getString("warn_kind") + "，请点击查看");
                        json.put("url", ar.getPrintscreen());

//                        HttpRequestUtils.POST_FORM(pushWeixinUrl + "/wechat/warn", json, "UTF-8");

                        WeChatPushUtil.weChatPush(json, oac.getAppId(), oac.getAppSecret(), oac.getAlarmTemplateId());

                        System.out.println("======================================微信消息推送==================================");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static BufferedImage imageToMat(Frame frame) {

        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        opencv_core.IplImage image = converter.convert(frame);
        if(image == null){
            return null;
        }
        BufferedImage bufferedImage = new BufferedImage(image.width(),
                image.height(), BufferedImage.TYPE_3BYTE_BGR);
        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        ((ByteBuffer) image.createBuffer()).get(data);
        return bufferedImage;
    }
}
