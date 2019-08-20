package com.java.common.utils;

import java.util.UUID;

/**
 * Created by Mr.BH
 */
public class PkIdUtils {

    /**
     * 获取主键 ID
     * @return
     */
    public static String getPkId(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
