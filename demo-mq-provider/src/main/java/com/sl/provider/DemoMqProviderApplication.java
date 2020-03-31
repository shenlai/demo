package com.sl.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoMqProviderApplication {

    //https://www.cnblogs.com/luochengqiuse/category/707239.html
    //https://blog.csdn.net/qq_21019419/article/details/84762265  同时支持queue和topic
    //https://www.cnblogs.com/duanjt/category/1349404.html


    public static void main(String[] args) {
        SpringApplication.run(DemoMqProviderApplication.class, args);
    }

}
