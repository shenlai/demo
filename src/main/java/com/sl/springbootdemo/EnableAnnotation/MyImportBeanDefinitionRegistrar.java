package com.sl.springbootdemo.EnableAnnotation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

//registerBeanDefinitions方法的参数有一个BeanDefinitionRegistry，该register可以用来向spring容器中注入bean
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(User.class);
        BeanDefinition beanDefinition = builder.getBeanDefinition();
        registry.registerBeanDefinition("user", beanDefinition);

        BeanDefinitionBuilder builder2 = BeanDefinitionBuilder.rootBeanDefinition(Role.class);
        BeanDefinition beanDefinition2 = builder2.getBeanDefinition();
        registry.registerBeanDefinition("role", beanDefinition2);
    }
}
