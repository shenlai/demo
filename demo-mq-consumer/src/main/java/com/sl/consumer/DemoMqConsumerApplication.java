package com.sl.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class DemoMqConsumerApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoMqConsumerApplication.class, args);
        Object connectionFactory = context.getBean("connectionFactory");
        Object connectionFactorySL = context.getBean("connectionFactorySl");
        System.out.println("demo-mq-consumer start success");

    }
}
