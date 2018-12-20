package com.sl.springbootdemo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(JestClient.class)
//@ConditionalOnClass(name="com.sl.springbootdemo.JestClient")
public  class JestAutoConfiguration{

}