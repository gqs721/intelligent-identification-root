package com.java.quartz.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
 
/**
 * RestTemplate配置模板
 *
 * @author zjz
 */
@Configuration
@Slf4j
public class RestTemplateConfig {
 
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }
 
    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        log.info("-----------------> ClientHttpRequestFactory 初始化");
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(1000*10);//单位为ms
        factory.setConnectTimeout(1000*10);//单位为ms
        return factory;
    }
 
}