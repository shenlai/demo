package com.sl.springlearning.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class Cat implements InitializingBean, DisposableBean {

    public Cat() {
        System.out.println("执行cat构造函数");
    }

    @Override
    public void destroy() throws Exception {
        // TODO Auto-generated method stub
        System.out.println("执行cat  destroy函数");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        System.out.println("执行cat...afterPropertiesSet初始化");
    }

}
