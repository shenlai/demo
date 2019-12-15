package com.sl.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class DemoMqConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoMqConsumerApplication.class, args);
    }
}
