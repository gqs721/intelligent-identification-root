package com.java.model.mybatis;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

/**
 * Created by Mr.BH
 * MyBatis 生成代码 自定义 类型转换器
 */
public class MyJavaTypeResolver extends JavaTypeResolverDefaultImpl {

    /**
     * 将tinyint转换为Integer，这里是关键所在
     */
    public MyJavaTypeResolver() {
        super();
        super.typeMap.put(-6, new JdbcTypeInformation("TINYINT", new FullyQualifiedJavaType(Integer.class.getName())));
    }

}
