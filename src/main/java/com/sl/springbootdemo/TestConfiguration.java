package com.sl.springbootdemo;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

//@SpringBootConfiguration
@Configuration
@ConditionalOnBean(PropertiesConfig.class)
public class TestConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public  EncodingConvert createUTF8EncodingConvert(){
        return  new UTF8EncodingConvert();
    }

    @Bean
    @Conditional(GBKCondition.class)  //使用自定义Condition
    //@ConditionalOnProperty(name="com.sl.Encoding",havingValue = "GBK",matchIfMissing = false)
    public  EncodingConvert createGBKEncodingConvert(){
        return  new GBKEncodingConvert();
    }
}

