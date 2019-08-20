package com.java.model.mybatis;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.BH
 */
public class MybatisGenerateApp {


    public static void main(String[] args) {

        List<String> warnings = new ArrayList<>();
        try {
            // 使用这个 获取的路径 会报错
//            String configFilePath = System.getProperty("user.dir").concat("/code/src/main/resources/mybatis/generatorConfig.xml");
            // 系统 mybatis 配置文件路径
            String configPath = "D:\\JAVA\\MyWorkSpaces\\Idea_projects\\intelligent-identification-root\\springboot-model\\src\\main\\resources\\mybatis\\generatorConfig.xml";

            boolean overwrite = true;
            File configFile = new File(configPath);
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
                    callback, warnings);
            myBatisGenerator.generate(null);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        for (String warning : warnings) {
            System.out.println(warning);
        }


    }
}
