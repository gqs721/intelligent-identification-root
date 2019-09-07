package com.java.quartz.utils;

import com.java.common.utils.File2CodeUtil;
import com.java.common.utils.HttpRequestUtils;
import com.java.common.utils.StringUtil;
import com.java.quartz.job.CollectJob;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 识别模型工具類
 */
@Component
@Slf4j
public class RecognitionModelUtil {

    /**
     * 处理图片，画出识别框
     * @param path
     * @param local
     * @throws IOException
     */
    public static String drawFrame(String deviceNumber, String dealPicPath, String path, String local, String dateStr, String fileName) {
        JSONArray array = JSONArray.fromObject(local);
        String picPath = "";
        if(array.size() != 0) {
            try {
                BufferedImage image = ImageIO.read(new File(path));

                Graphics g = image.getGraphics();
                g.setColor(Color.green);//画笔颜色
                for (int i = 0; i < array.size(); i++) {
                    JSONArray obj = array.getJSONArray(i);
                    //矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
                    g.drawRect(obj.getInt(0), obj.getInt(1), obj.getInt(2) - obj.getInt(0), obj.getInt(3) - obj.getInt(1));
                    //g.dispose();
                    System.out.println("识别度：" + obj.getInt(4) / 1000 + "%");
                }
                String outPath = dealPicPath + deviceNumber + "\\" + dateStr + "\\dealImage\\";
                File file = new File(outPath);
                if (!file.exists()) {//如果文件夹不存在
                    file.mkdirs();//创建文件夹
                }
                String outName = fileName;
                FileOutputStream out = new FileOutputStream(outPath + outName);//输出图片的地址
                ImageIO.write(image, "jpeg", out);

                picPath = outPath + outName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return picPath;
    }

    /**
     * 安全帽模型调用
     * @param picPath
     * @param ip
     * @param port
     * @return
     */
    public static String safetyHelmetModel(String picPath, String ip, int port){
        Map<String, String> params = new HashMap<>();

        params.put("Fps","30");
        params.put("SamplingRate","10");
        params.put("Width","1920");
        params.put("Height","1080");

        String base64Pic = "";
        try {
            base64Pic = File2CodeUtil.encodeBase64File(picPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        base64Pic = base64Pic.replace("\r\n","");

        params.put("ImageData",base64Pic);
        String result = null;
        try {
            for (int i = 0; i < 3;i++) {
                result = HttpRequestUtils.sendPost("http://" + ip + ":" + port + "/hardhat?id=0&init=false&close=false", JSONObject.fromObject(params), "utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("安全帽返回结果值："+result);

        result = drawFrame("","",picPath,result,"","");

        return result;
    }

    /**
     * 安全帽模型调用
     * @param base64Pic
     * @param ip
     * @param port
     * @return
     */
    public static String safetyHelmetModelByBase64Pic(String base64Pic, String ip, int port, String deviceNumber){
        Map<String, String> params = new HashMap<>();

        params.put("guid",deviceNumber);
        params.put("model","hardhat");
        params.put("width","1920");
        params.put("height","1080");

        base64Pic = base64Pic.replace("\r\n","");

        params.put("imagedata",base64Pic);
        String result = null;
        try {
            for (int i = 0; i < 3;i++) {
                result = HttpRequestUtils.sendPost("http://" + ip + ":" + port + "/buildingsite/pre", JSONObject.fromObject(params), "utf-8");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("安全帽返回结果值："+result);

//        try {
//            result = drawFrame(picPath,result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return result;
    }

    /**
     * 吸烟模型调用
     * @param picPath
     * @param ip
     * @param port
     */
    public static String smokingModel(String picPath, String ip, int port){
        Map<String, String> params = new HashMap<>();

        params.put("Fps","30");
        params.put("SamplingRate","10");
        params.put("Width","1920");
        params.put("Height","1080");

        String base64Pic = "";
        try {
            base64Pic = File2CodeUtil.encodeBase64File(picPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        base64Pic = base64Pic.replace("\r\n","");

        params.put("ImageData",base64Pic);
        String result = null;
        try {
            result = HttpRequestUtils.sendPost("http://" + ip + ":" + port + "/get_pose?id=0&init=true&close=false", JSONObject.fromObject(params),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("吸烟返回结果值："+result);
        if(StringUtil.isEmpty(result)) {
            String result1 = null;
            try {
                for (int i = 0; i < 3; i++)
                    result1 = HttpRequestUtils.sendPost("http://" + ip + ":" + port + "/get_pose?id=0&init=false&close=false", JSONObject.fromObject(params), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("吸烟返回结果值1：" + result1);

             result = drawFrame("","",picPath, result1,"","");
        }
        return result;
    }

    /**
     * 吸烟模型调用
     * @param base64Pic
     * @param ip
     * @param port
     */
    public static String smokingModelByBase64Pic(String base64Pic, String ip, int port){
        Map<String, String> params = new HashMap<>();

        params.put("Fps","30");
        params.put("SamplingRate","10");
        params.put("Width","1920");
        params.put("Height","1080");

        base64Pic = base64Pic.replace("\r\n","");

        params.put("ImageData",base64Pic);
        String result = "";
        try {
            result = HttpRequestUtils.sendPost("http://" + ip + ":" + port + "/get_pose?id=0&init=true&close=false", JSONObject.fromObject(params),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("吸烟返回结果值："+result);
        if(StringUtil.isEmpty(result)) {
//            String result1 = null;
            try {
                for (int i = 0; i < 3; i++)
                    result = HttpRequestUtils.sendPost("http://" + ip + ":" + port + "/get_pose?id=0&init=false&close=false", JSONObject.fromObject(params), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
            JSONArray array = JSONArray.fromObject(result);
            if(array.isEmpty()){
                result = "";
            }
            System.out.println("吸烟返回结果值：" + result);
//            try {
//                result = drawFrame(picPath, result1);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }else{
            result = "";
        }
        return result;
    }

    /**
     * 人脸识别模型调用
     * @param picPath
     * @param ip
     * @param port
     */
    public static String faceRecognitionModel(String picPath, String ip, int port){
        Map<String, Object> params = new HashMap<>();

        params.put("guid","1");
        params.put("kind",0);

        String base64Pic = "";
        try {
            base64Pic = File2CodeUtil.encodeBase64File(picPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        base64Pic = base64Pic.replace("\r\n","");

        params.put("face_image",base64Pic);
        String result = null;
        try {
            result = HttpRequestUtils.sendPost("http://" + ip + ":" + port + "/api/v1/detect", JSONObject.fromObject(params),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("人脸识别返回结果值："+result);

        return result;
    }

    /**
     * 人脸识别模型调用
     * @param base64Pic
     * @param ip
     * @param port
     */
    public static String faceRecognitionModelByBase64Pic(String base64Pic, String ip, int port, String guid, Integer faceType){
        Map<String, Object> params = new HashMap<>();

        params.put("guid",guid);
//        params.put("kind",0);//陌生人
//        params.put("kind",1);//白名单
        params.put("kind",faceType);//黑名单

        base64Pic = base64Pic.replace("\r\n","");

        params.put("face_image",base64Pic);
        String result = null;
        try {
//            result = HttpRequestUtils.sendPost("http://" + ip + ":" + port + "/api/v1/detect", JSONObject.fromObject(params),"utf-8");
            result = CollectJob.restTemplate.postForObject("http://" + ip + ":" + port + "/api/v1/detect", params, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("人脸识别返回结果值："+result);

        return result;
    }


    /**
     * 模型调用
     * @param base64Pic
     * @param ip
     * @param port
     * @return
     */
    public static String modelByBase64Pic(String base64Pic, String ip, int port, String deviceNumber, String model){
        Map<String, String> params = new HashMap<>();

        params.put("guid",deviceNumber);
        params.put("model",model);
        params.put("width","1920");
        params.put("height","1080");

        base64Pic = base64Pic.replace("\r\n","");

        params.put("imagedata",base64Pic);
        String result = null;
        try {
            Date time1 = new Date();
//            result = HttpRequestUtils.sendPost("http://" + ip + ":" + port + "/buildingsite/pre", JSONObject.fromObject(params), "utf-8");
//            RestTemplate restTemplate = new RestTemplate();
//            result = restTemplate.postForObject("http://" + ip + ":" + port + "/buildingsite/pre",params,String.class);
            result = CollectJob.restTemplate.postForObject("http://" + ip + ":" + port + "/buildingsite/pre", params, String.class);
            Date time2 = new Date();
            System.out.println("=============================请求时间："+(time2.getTime()-time1.getTime()));

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(model + "模型返回结果值："+result);
        return result;
    }
}