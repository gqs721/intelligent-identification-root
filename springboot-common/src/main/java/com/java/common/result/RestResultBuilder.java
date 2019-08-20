package com.java.common.result;

import com.java.common.enums.ResultCodeEnum;

import java.util.HashMap;

/**
 * Created by Mr.BH
 *
 * @param <T>
 */
public class RestResultBuilder<T> {

    private int code = 0;

    private String msg = "返回成功";

    private T data;

    public RestResultBuilder<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public RestResultBuilder<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public RestResultBuilder<T> setData(T data) {
        this.data = data;
        return this;
    }

    public RestResult<T> build() {
        return new RestResult<T>(code, msg, data != null ? data : (T) new HashMap());
    }

    public RestResult<T> error(String msg) {
        return new RestResult<T>(ResultCodeEnum.ERROR.getCode(), msg, (T) new HashMap());
    }

    public RestResult<T> success() {
        return new RestResult<T>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMsg(), (T) new HashMap());
    }

    public RestResult<T> success(String msg) {
        return new RestResult<T>(ResultCodeEnum.SUCCESS.getCode(), msg, (T) new HashMap());
    }

    public RestResult<T> success(String msg, T data) {
        return new RestResult<T>(ResultCodeEnum.SUCCESS.getCode(), msg, data != null ? data : (T) new HashMap());
    }


}
