package com.sl.anno.cap7.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 实现 Bean实现 InitializingBean 和 DisposableBean接口
 */
@Component
public class Train implements InitializingBean, DisposableBean {

    public Train() {
        System.out.println("Train......constructor............");
    }

    //当bean销毁时,会把单实例bean进行销毁
    @Override
    public void destroy() throws Exception {
        System.out.println("Train......destory......");
        //logger.error
    }

    //当我们的bean属性赋值和初始化完成时调用
    //当beanFactory创建好对象,且把bean所有属性设置好之后,会调这个方法,相当于初始化方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("Train.......afterPropertiesSet()...");

    }

}
