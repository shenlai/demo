package com.sl.springbootdemo;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootConfiguration
public class TestConfig {

    @Bean
    public  EncodingConvert createUTF8EncodingConvert(){
        return  new UTF8EncodingConvert();
    }

    @Bean
    public  EncodingConvert createGBKEncodingConvert(){
        return  new GBKEncodingConvert();
    }
}
