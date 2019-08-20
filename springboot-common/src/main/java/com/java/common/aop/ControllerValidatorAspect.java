package com.java.common.aop;

import com.java.common.enums.ResultCodeEnum;
import com.java.common.result.RestResult;
import com.java.common.result.ResultUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Mr.BH
 * 参数验证
 */
@Aspect
@Component
public class ControllerValidatorAspect {

    @Autowired
    private MessageSource messageSource;

    @Around("execution(* com.java.*.controller.*.*(..)) && args(..,result)")
    public RestResult doAround(ProceedingJoinPoint pjp, BindingResult result) throws Throwable {

        // 判断 参数是否有错
        if (result.hasErrors()){
            Map resultMsg = new HashMap();
            // 获取错误 字段集合
            List<FieldError> fieldErrors = result.getFieldErrors();

            // 获取本地 locale,zh_CN
            Locale currentLocale = LocaleContextHolder.getLocale();

            // 遍历错误字段 获取错误消息
            for (FieldError fd:fieldErrors) {
                // 获取错误消息
                String errorMessage = messageSource.getMessage(fd,currentLocale);
                // 添加到错误消息集合
                resultMsg.put(fd.getField(),errorMessage);
            }
            // 错误处理
            return ResultUtils.error(ResultCodeEnum.ILLEGAL_ARGUMENT.getCode(), ResultCodeEnum.ILLEGAL_ARGUMENT.getMsg(),resultMsg);
        }

        RestResult restResult = null;

        restResult = (RestResult) pjp.proceed();

        return restResult;
    }


}
