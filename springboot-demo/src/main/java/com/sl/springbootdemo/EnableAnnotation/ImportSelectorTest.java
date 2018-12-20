package com.sl.springbootdemo.EnableAnnotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;


//selectImports方法返回一个class（全称），该class会被spring容器所托管起来
public class ImportSelectorTest implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        /**
         * xxxx逻辑
         */

        return new String[]{
                "com.sl.springbootdemo.EnableAnnotation.ImportTestClass"

                //Role.class.getName()

        };
    }
}
