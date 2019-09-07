package com.java.quartz.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FfmpegUtil {

    public static void main(String[] args) throws Exception {
        String rtspPath = "\"rtsp://47.92.229.202:2345/34020000001320000003_34020000001320000003\"";
        String ffmpegPath = FfmpegUtil.class.getClassLoader().getResource("ffmpeg/ffmpeg.exe").getPath();
        String savePicPath = "C:\\image\\";
        File file=new File(savePicPath);
        if(!file.exists()){//如果文件夹不存在
            file.mkdir();//创建文件夹
        }
        MyExecutor  myExecutor = new MyExecutor();
        try {
            myExecutor.ffmpegFun(ffmpegPath, rtspPath, savePicPath,"image%03d.jpg");
//            executeCodecs(ffmpegPath, rtspPath, savePicPath,"image%03d.jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("顺序执行");
    }

    /**
     * 异步执行ffmpeg拉流截图
     * @param ffmpegPath
     * @param rtspPath
     * @param savePicPath
     * @param picName
     */
    public static void executeFfmpeg(String ffmpegPath, String rtspPath, String savePicPath, String picName){
        MyExecutor  myExecutor = new MyExecutor();
        try {
            myExecutor.ffmpegFun(ffmpegPath, rtspPath, savePicPath, picName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 视频转码
     * @param rtspPath    截图保存路径
     * @return
     * @throws Exception
     */
    public boolean executeCodecs(String ffmpegPath, String rtspPath, String savePicPath, String picName){
//        System.out.println("reources路径："+ path);
//        String ffmpegPath = path;

        // 创建一个List集合来保存从视频中截取图片的命令
        List<String> cutpic = new ArrayList<String>();
        cutpic.add(ffmpegPath);
        cutpic.add("-i");
        cutpic.add(rtspPath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
        cutpic.add("-y");
        cutpic.add("-f");
        cutpic.add("image2");
        cutpic.add("-s");
        cutpic.add("1920X1080");
        cutpic.add("-r");
        cutpic.add("5");
        cutpic.add(savePicPath + picName);

        StringBuffer test = new StringBuffer();
        for (int i = 0; i < cutpic.size(); i++)
            test.append(cutpic.get(i) + " ");
        System.out.println(test);

        boolean mark = true;
        CmdExecuter.exec(cutpic);
//        ProcessBuilder builder = new ProcessBuilder();
//        Process proc =null;
//        try {
//            builder.command(cutpic);
//            builder.redirectErrorStream(true);
//            // 如果此属性为 true，则任何由通过此对象的 start() 方法启动的后续子进程生成的错误输出都将与标准输出合并，
//            //因此两者均可使用 Process.getInputStream() 方法读取。这使得关联错误消息和相应的输出变得更容易
//            proc = builder.start();
//        } catch (Exception e) {
//            mark = false;
//            System.out.println(e);
//            e.printStackTrace();
//        }finally{
//            try {
//                proc.waitFor();
//            } catch (Exception e) {
//                e.printStackTrace();
//                mark = false;
//            }
//        }
        return mark;
    }
}
