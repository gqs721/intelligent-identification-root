package com.java.system.controller;

import com.java.common.result.RestResult;
import com.java.common.result.RestResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Mr.BH
 */
@RestController
public class APIHttpErrorController implements ErrorController {

    public static final String ERROR_PATH = "/error";

    @Autowired
    private ErrorAttributes errorAttributes;


    @RequestMapping(value = ERROR_PATH)
    public <T> RestResult<T> error(HttpServletRequest request) {
        return getBody(request, false);
    }


    private <T> RestResult<T> getBody(HttpServletRequest request, Boolean includeStackTrace) {
        Map<String, Object> errorAttributes = getErrorAttributes(request, includeStackTrace);
        Integer status = (Integer) errorAttributes.get("status");
        String message = (String) errorAttributes.get("message");
        return new RestResultBuilder<T>().setCode(status)
                .setMsg(message).build();
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request,
                                                   boolean includeStackTrace) {
        ServletWebRequest servletWebRequest = new ServletWebRequest(request);
        return this.errorAttributes.getErrorAttributes(servletWebRequest,
                includeStackTrace);
    }


    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }


}
