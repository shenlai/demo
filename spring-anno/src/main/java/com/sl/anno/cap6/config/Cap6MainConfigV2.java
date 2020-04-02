package com.sl.anno.cap6.config;

import com.sl.anno.cap1.model.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Cap6MainConfigV2 {

    //容器启动时初始化person的bean实例
    @Bean("person")
    public Person person() {
        return new Person("james", 20);
    }


//    @Bean
//    public JamesFactoryBean jamesFactoryBean() {
//        return new JamesFactoryBean();
//    }
}
