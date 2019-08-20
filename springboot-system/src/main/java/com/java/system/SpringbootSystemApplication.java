package com.java.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.java")
@ServletComponentScan
@EnableSwagger2
@MapperScan("com.java.model.dao")
public class SpringbootSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSystemApplication.class, args);
	}
}
