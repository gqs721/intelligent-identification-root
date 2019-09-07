package com.java.common.utils;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.BH
 * LiveGBS请求方法
 */
public class LiveGBSUtil {

    /**
     * 获取直播视频流
     * @param ip
     * @param port
     * @return
     */
    public static String stream_list(String ip, Integer port){
        Map<String, Object> params = new HashMap<>();
        String result = HttpRequestUtils.sendGet("http://" + ip + ":" + port + "/api/v1/stream/list", params);
        return result;
    }

    /**
     * 获取设备列表
     * @param ip
     * @param port
     * @param params
     * @return
     */
    public static String device_list(String ip, Integer port,  Map<String, Object> params){
        String result = null;
        try {
            result = HttpRequestUtils.sendGet("http://" + ip + ":" + port + "/api/v1/device/list", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取设备通道列表
     * @param ip
     * @param port
     * @param params
     * @return
     */
    public static String device_channellist(String ip, Integer port,  Map<String, Object> params){
        String result = null;
        try {
            result = HttpRequestUtils.sendGet("http://" + ip + ":" + port + "/api/v1/device/channellist", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 开始直播
     * @param ip
     * @param port
     * @param params
     * @return
     */
    public static String stream_start(String ip, Integer port,  Map<String, Object> params){
        String result = null;
        try {
            result = HttpRequestUtils.sendGet("http://" + ip + ":" + port + "/api/v1/stream/start", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 停止直播
     * @param ip
     * @param port
     * @param params
     * @return
     */
    public static String stream_stop(String ip, Integer port,  Map<String, Object> params){
        String result = null;
        try {
            result = HttpRequestUtils.sendGet("http://" + ip + ":" + port + "/api/v1/stream/stop", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 实现实时截图
     * @param ip
     * @param port
     * @param params
     * @return
     */
    public static String device_channelsnap(String ip, Integer port,  Map<String, Object> params, String outPath, String picName){
        // 实时截图参数
        params.put("realtime", true);
        String result = null;
        try {
            result = HttpRequestUtils.sendGet1("http://" + ip + ":" + port + "/api/v1/device/channelsnap", params, outPath, picName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 开始回放
     * @param ip
     * @param port
     * @param params
     * @return
     */
    public static String playback_start(String ip, Integer port,  Map<String, Object> params){
        String result = null;
        try {
            result = HttpRequestUtils.sendGet("http://" + ip + ":" + port + "/api/v1/playback/start", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
