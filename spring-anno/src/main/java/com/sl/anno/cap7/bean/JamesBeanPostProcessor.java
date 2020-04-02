package com.sl.anno.cap7.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class JamesBeanPostProcessor implements BeanPostProcessor {

    /**
     * BeanPostProcessor类[interface]: bean的后 置处理器,在bean初始化之前调用进行拦截
     * 在初始化之前进行后置处理工作(在init-method之前),
     * 什么时候调用:它任何初始化方法调用之前
     * (比如在InitializingBean的afterPropertiesSet初始化之前,或自定义init-method调用之前使用)
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //返回一个的对象(传过来的对象)
        //在初始化方法调用之前进行后置处理工作,
        //什么时候调用它: init-method=init之前调用
        System.out.println("postProcessBeforeInitialization...." + beanName + "..." + bean);
        return bean;
    }

    /**
     * b> postProcessAfterInitialization():在初始化之后进行后置处理工作,
     * 比如在InitializingBean的afterPropertiesSet()
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization...." + beanName + "..." + bean);
        return bean;
    }
}
