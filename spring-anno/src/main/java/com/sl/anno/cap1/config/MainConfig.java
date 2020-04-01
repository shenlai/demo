package com.sl.anno.cap1.config;

import com.sl.anno.cap1.model.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MainConfig {

    // 不指定name 以方法名作beanID
    @Bean(name = "personXX")
    public Person person(){
        return  new Person("bean注解注入",12);
    }

}
