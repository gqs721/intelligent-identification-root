package com.java.common.utils;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public static void createFile(String path, String fileName){
//        System.getProperty("user.dir")
        File f = new File(path);
        if(!f.exists()){
            f.mkdirs();
        }
        File file = new File(f,fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        createFile("d:\\test\\test","test.txt");
    }
}
