package com.sl.springbootdemo.extendsion;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;

@Order(1)
public class ApplicationContextInitializerTest implements org.springframework.context.ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        System.out.println("这里是自定义ApplicationContextInitializer，可以用来操作applicationContext");

    }
}
