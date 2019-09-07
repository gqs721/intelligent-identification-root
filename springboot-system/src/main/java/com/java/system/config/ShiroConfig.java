package com.java.system.config;

import com.java.system.secutity.KickoutSessionControlFilter;
import com.java.system.secutity.MyShiroRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;


@Configuration
@Slf4j
public class ShiroConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;

    @Value("${spring.redis.password}")
    private String redisPassword;

    @Value("${config.kickout_after}")
    private boolean kickoutAfter;

    @Value("${config.max_session}")
    private Integer maxSession;

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 没有登陆的用户只能访问登陆页面
        shiroFilterFactoryBean.setLoginUrl("/sys/admin/request_failed");
//        shiroFilterFactoryBean.setLoginUrl("/");

        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/auth/index");
        // 未授权界面; ----这个配置了没卵用，具体原因想深入了解的可以自行百度
        //shiroFilterFactoryBean.setUnauthorizedUrl("/auth/403");
        //自定义拦截器
        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
        //限制同一帐号同时在线的个数。
        filtersMap.put("kickout", kickoutSessionControlFilter());
        shiroFilterFactoryBean.setFilters(filtersMap);
        // 权限控制map.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        // 因为 管理员登录模块 和 用户管理模块 有命名冲突，规范 管理员登录 即：sys
        filterChainDefinitionMap.put("/sys/admin/login", "anon");
        filterChainDefinitionMap.put("/sys/admin/request_failed", "anon");
        filterChainDefinitionMap.put("/sys/admin/kickout", "anon");// 被踢出后调用路径
        filterChainDefinitionMap.put("/sys/admin/registerSave", "anon");// 注册管理员
        filterChainDefinitionMap.put("/sys/admin/image", "anon");// 获取图片验证码
        filterChainDefinitionMap.put("/sys/admin/sendMail", "anon");// 发送邮箱验证码
        filterChainDefinitionMap.put("/sys/admin/verifyMail", "anon");// 验证邮箱是否注册
        filterChainDefinitionMap.put("/sys/admin/verifyMailCode", "anon");// 验证邮箱验证码
        filterChainDefinitionMap.put("/sys/admin/resetPassword", "anon");// 忘记密码的重置密码

        filterChainDefinitionMap.put("/sys/common/**", "anon");// 保存openId
//        filterChainDefinitionMap.put("/sys/common/unbindOpenId", "anon");// 保存openId
//        filterChainDefinitionMap.put("/druid/**", "anon");
        // 微信端操作接口开放
        filterChainDefinitionMap.put("/push/weixin/save", "anon");
        filterChainDefinitionMap.put("/push/weixin/deleteByOpenId", "anon");
        filterChainDefinitionMap.put("/push/weixin/update", "anon");
        filterChainDefinitionMap.put("/push/weixin/selectByOpenId", "anon");
        filterChainDefinitionMap.put("/push/weixin/wxSetStatus", "anon");

        //swagger接口权限 开放
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/configuration/security", "anon");
        filterChainDefinitionMap.put("/configuration/ui", "anon");

//        filterChainDefinitionMap.put("/device/group/getAllByUserId", "anon");

//        filterChainDefinitionMap.put("/auth/login", "anon");
//        filterChainDefinitionMap.put("/auth/logout", "logout");
//        filterChainDefinitionMap.put("/auth/kickout", "anon");

        // 自定义过滤器 对所有请求都进行处理
        filterChainDefinitionMap.put("/image/**", "anon");
        filterChainDefinitionMap.put("/**", "authc,kickout");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.
        securityManager.setRealm(myShiroRealm());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis，注释就不会有登录踢出效果
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
     *
     * @return
     */
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();

//        log.info("----------------路径："+System.getProperty("user.dir")+"----------------");
////        createFile(System.getProperty("user.dir")+"/test_image", "test.txt");
//
//        GenerateQRCodeUtil.generateQR("生成成功",System.getProperty("user.dir") + "/test_image");
        return myShiroRealm;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisHost);
        redisManager.setPort(redisPort);
//        redisManager.setExpire(1800);// 配置缓存过期时间，不设置默认永久不过期
        redisManager.setTimeout(0);
        redisManager.setPassword(redisPassword);
        return redisManager;
    }

    /**
     * Session Manager
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());

        //全局会话超时时间（单位毫秒），默认30分钟  暂时设置为10秒钟 用来测试, 设置的最大时间，正负都可以，为负数时表示永不超时。
        sessionManager.setGlobalSessionTimeout(-1000l);
        //是否开启删除无效的session对象  默认为true
//        sessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true, 定时清理失效会话, 清理用户直接关闭浏览器造成的孤立会话
//        sessionManager.setSessionValidationSchedulerEnabled(true);
        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        //暂时设置为 5秒 用来测试
//        sessionManager.setSessionValidationInterval(5000);

        //取消url 后面的 JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * 限制同一账号登录同时登录人数控制
     *
     * @return
     */
    @Bean
    public KickoutSessionControlFilter kickoutSessionControlFilter() {
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        kickoutSessionControlFilter.setCacheManager(cacheManager());
        kickoutSessionControlFilter.setSessionManager(sessionManager());
        kickoutSessionControlFilter.setKickoutAfter(kickoutAfter); // false踢出之前登录的/true之后登录的用户
        kickoutSessionControlFilter.setMaxSession(maxSession); // 同一个帐号最大会话数
        kickoutSessionControlFilter.setKickoutUrl("/sys/admin/kickout");
        return kickoutSessionControlFilter;
    }


    /***
     * 授权所用配置
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /***
     * 使授权注解起作用不如不想配置可以在pom文件中加入
     * <dependency>
     *<groupId>org.springframework.boot</groupId>
     *<artifactId>spring-boot-starter-aop</artifactId>
     *</dependency>
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * Shiro生命周期处理器
     *
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}
