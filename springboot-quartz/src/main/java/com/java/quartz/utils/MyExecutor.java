package com.java.quartz.utils;

import com.java.model.dao.IdentificationLibraryMapper;
import com.java.model.domain.DeviceConfig;
import com.java.model.domain.ServerConfig;
import com.java.model.domain.StreamMediaConfig;
import net.sf.json.JSONObject;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class MyExecutor {
    public  ExecutorService executor = Executors.newCachedThreadPool();

//    public static Future future = null;

    public void ffmpegFun(String ffmpegPath, String rtspPath, String savePicPath, String picName) throws Exception {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    FfmpegUtil.executeFfmpeg(ffmpegPath, rtspPath, savePicPath, picName);
                } catch (Exception e) {
                    throw new RuntimeException("报错啦！！");
                }
            }
        });
    }

    public void javacvFun(JSONObject json, String rtspPath, DeviceConfig dc, List<ServerConfig> scList, StreamMediaConfig smc, IdentificationLibraryMapper libraryMapper, Integer guid) throws Exception {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
//                    CollectJob.deviceList.add(dc.getDeviceNumber());
                    System.out.println("启动任务:" + dc.getDeviceNumber());
//                    OpencvUtil.executeJavacv(json, rtspPath, dc, scList, libraryMapper);
//                    OpencvUtil.dealRTSP();
                } catch (Exception e) {
                    throw new RuntimeException("报错啦！！");
                }
            }
        });
    }

    public void javacvFun1(OpencvUtil op, FFmpegFrameGrabber grabber, DeviceConfig dc) throws Exception {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
//                    OpencvUtil op = new OpencvUtil();
//                    op.dealRTSP(grabber, dc);
                } catch (Exception e) {
                    throw new RuntimeException("报错啦！！");
                }
            }
        });
    }

    public void dealIdentificationFun(List<ServerConfig> scList, DeviceConfig dc, IdentificationLibraryMapper libraryMapper, JSONObject json) throws Exception {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    OpencvUtil op = new OpencvUtil();
//                    op.dealIdentification(scList, dc, null, json);
                } catch (Exception e) {
                    throw new RuntimeException("报错啦！！");
                }
            }
        });
    }
}
