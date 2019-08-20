package com.java.common.exceptions;

/**
 * Created by Mr.BH
 */
public class RoleNotExistException extends APIRunTimeException {

    public RoleNotExistException(int code, String msg) {
        super(code, msg);
    }
}
