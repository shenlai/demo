package com.sl.springlearning.extension;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor执行postProcessBeanFactory方法");
        int count = beanFactory.getBeanDefinitionCount();
        String[] beanNames = beanFactory.getBeanDefinitionNames();
        System.out.println("当前BeanFactory中有：" + count + "个Bean实例");
        System.out.println(Arrays.asList(beanNames));
    }
}
