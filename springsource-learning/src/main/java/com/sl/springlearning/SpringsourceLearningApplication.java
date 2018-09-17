package com.sl.springlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class SpringsourceLearningApplication {

    public static void main(String[] args) {
       ApplicationContext context = SpringApplication.run(SpringsourceLearningApplication.class, args);
        System.out.println(context.getBean("carFactoryBean"));//得到的是car:  com.sl.springlearning.bean.Car@16eccb2e
        System.out.println(context.getBean("&carFactoryBean"));//得到的是car工厂  :com.sl.springlearning.bean.CarFactoryBean@495ee280


    }
}
