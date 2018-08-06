package com.sl.springbootdemo;

import com.sl.springbootdemo.EnableAnnotation.TomcatProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@SpringBootApplication

//@EnableAutoConfiguration
//@ComponentScan

@EnableConfigurationProperties  //用来启用一个特性，这个特性可以把配置文件的属性注入到bean里面
@ComponentScan
public class SpringbootdemoApplication {

	public static void main(String[] args) {
	 	ConfigurableApplicationContext context = SpringApplication.run(SpringbootdemoApplication.class,args);

	 	System.out.println(context.getBeansOfType(TomcatProperties.class));





		//System.out.println(context.getBeansOfType(EncodingConvert.class));

		//System.out.println(context.getBean("jestAutoConfiguration"));

		//System.out.println(context.getEnvironment().getProperty("file.encoding"));

		//context.close();

		//SpringApplication.run(SpringbootdemoApplication.class, args);
	}
}
