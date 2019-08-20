package com.java.common.aop;

import com.java.common.utils.DateUtil;
import com.java.model.dao.SysLogMapper;
import com.java.model.domain.Admin;
import com.java.model.domain.SysLog;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Mr.BH
 */

@Aspect
@Component
public class WebLogAspect {

    @Autowired
    private SysLogMapper sysLogMapper;

    private Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<Long>();

    @Pointcut("execution(* com.java.*.controller.*.*(..))")
    public void webLog() {
    }


    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {

        startTime.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + request.getParameterMap());

    }

    @After("webLog()")
    public void doAfter() {
        // 处理完请求，返回内容
        logger.info("耗时（毫秒） : " + (System.currentTimeMillis() - startTime.get()));
    }


    public static Admin u = new Admin();

    /**
     * 获取用户登录成功传过来的user值
     * @param user
     * @return
     */
    public static Admin getUser(Admin user){
        u = user;
        return u;
    }

    //切面 配置通知
    /**
     * 记录操作日志
     * @param joinPoint
     */
    @AfterReturning("webLog()")
    public void saveSysLog(JoinPoint joinPoint) {
//        logger.info("切面。。。。。");
        //保存日志
        SysLog sysLog = new SysLog();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        //获取操作
        MyLog myLog = method.getAnnotation(MyLog.class);
        // 只记录需要记录的操作日志
        if (myLog != null) {
            String value = myLog.value();
            sysLog.setOperation(value);//保存获取的操作
        } else {
            return;
        }

        //获取请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        //获取请求的方法名
        String methodName = method.getName();
        sysLog.setMethod(className + "." + methodName);

        //请求的参数
//        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
//        String params = args.toString();
//        sysLog.setParams(params);

        sysLog.setCreateTime(DateUtil.getCurrDate());
        Admin admin = (Admin)SecurityUtils.getSubject().getPrincipal();
        //获取用户名
        if(admin == null && "login".equals(methodName) && u != null){
            sysLog.setUserName(u.getUserName());
        }else{
            sysLog.setUserName(admin.getUserName());
        }

        //获取用户ip地址
        // 接收到请求，记录请求内容
        try {
            sysLog.setIp(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //调用service保存SysLog实体类到数据库
        sysLogMapper.insertSelective(sysLog);
    }
    public static void main(String[] args) {
        try {
            String str= InetAddress.getLocalHost().getHostAddress();
            System.out.println(str);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
