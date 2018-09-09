package com.sl.springbootdemo.EnableAnnotation;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class ImportBeanDefinitionRegistrarTest implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // new一个RootBeanDefinition
        BeanDefinitionBuilder rootBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(ImportTestClass.class);
        //RootBeanDefinition rootBeanDefinition2 = new RootBeanDefinition(ImportTestClass.class);
        // 注册一个name为importTestClassInstance的bean
        registry.registerBeanDefinition("importTestClassInstance", rootBeanDefinition.getBeanDefinition());
        //registry.registerBeanDefinition("importTestClassInstance",rootBeanDefinition2);
    }
}
