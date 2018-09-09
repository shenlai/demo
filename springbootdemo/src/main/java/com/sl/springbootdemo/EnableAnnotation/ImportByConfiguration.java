package com.sl.springbootdemo.EnableAnnotation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ImportByConfiguration {

    @Bean
    public  ImportTestClass createImportTestClass(){
        return new ImportTestClass();
    }
}
