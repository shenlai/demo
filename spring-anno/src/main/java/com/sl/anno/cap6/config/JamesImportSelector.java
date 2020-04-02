package com.sl.anno.cap6.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * ImportSelector接口返回一个class（全称），该class会被spring容器所托管起来
 * <p>
 * 如果只是用@Component注释ImportSelector的实现类JamesImportSelector 该类的selectImports方法是不会被调用的
 * 我们需要使用@import导入自动配置文件选择器
 */
public class JamesImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //返回全类名的bean
        return new String[]{"com.sl.anno.cap6.bean.Fish", "com.sl.anno.cap6.bean.Tiger"};
    }
}
