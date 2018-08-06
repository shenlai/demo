package com.sl.springbootdemo.EnableAnnotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;


//selectImports方法返回一个class（全称），该class会被spring容器所托管起来
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        System.out.println(EnableLog.class.getName());
        System.out.println(importingClassMetadata.getAnnotationAttributes(EnableLog.class.getName()));
        //上面可以获取到注解的详细信息，然后根据信息去动态的返回需要被Spring容器管理的bean


        //return new String[0];
        return new String[]{"com.sl.springbootdemo.EnableAnnotation.User",
                Role.class.getName()
        };
    }
}
