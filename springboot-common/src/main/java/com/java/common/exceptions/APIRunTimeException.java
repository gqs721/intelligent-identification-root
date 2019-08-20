package com.java.common.exceptions;

/**
 * Created by Mr.BH
 */
public class APIRunTimeException extends RuntimeException {

    private int code;

    private String msg;

    public APIRunTimeException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
