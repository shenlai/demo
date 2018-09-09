package com.sl.springlearning.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Dog  //implements ApplicationContextAware
{

    public Dog() {
        System.out.println("执行 dog constructor...");
    }

    //对象创建并赋值之后调用
    @PostConstruct
    public void init() {
        System.out.println("执行 Dog....@PostConstruct...");
    }

    //容器移除对象之前
    @PreDestroy
    public void detory() {
        System.out.println("执行 Dog....@PreDestroy...");
    }

    //容器移除对象之前
    @PreDestroy
    public void detory2() {
        System.out.println("执行 Dog....@PreDestroy...第二个");
    }

}
