package com.java.common.result;

public class ResultUtils {

    public static RestResult success(Object data){
        return new RestResultBuilder().setCode(0).setMsg("请求成功").setData(data).build();
    }

    public static RestResult error(Integer code,String msg){
        return new RestResultBuilder().setCode(code).setMsg(msg).build();
    }

    public static RestResult error(Integer code, String msg, Object data){
        return new RestResultBuilder().setCode(code).setMsg(msg).setData(data).build();
    }
}
