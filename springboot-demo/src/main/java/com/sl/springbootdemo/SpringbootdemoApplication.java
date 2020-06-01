package com.sl.springbootdemo;

import com.sl.springbootdemo.Events.ApplicationEventTest;
import com.sl.springbootdemo.Listeners.ApplicationListenerTest;
import com.sl.springbootdemo.extendsion.ApplicationContextInitializerTest;
import configtest.Configdemo;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.config.DelegatingApplicationListener;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.awt.*;
import java.util.Set;

@SpringBootApplication

//@EnableAutoConfiguration
//@ComponentScan


//@EnableConfigurationProperties  //用来启用一个特性，这个特性可以把配置文件的属性注入到bean里面 一般和ConfigurationProperties一起使用
//@ComponentScan
public class SpringbootdemoApplication {

	public static void main(String[] args) {

		//CommandLineRunner、ApplicationRunner
  		//扩展
		//SpringApplication application = new SpringApplication(SpringbootdemoApplication.class);
		//添加到applicaiton上下文中或者通过配置项配置context.initializer.class,多个使用逗号分隔，也可在通过在META-INF/spring.factories中配置
		//application.addInitializers(new ApplicationContextInitializerTest());
		//application.setBannerMode(Banner.Mode.OFF);
		//application.run(args).close();



		//ApplicationContext
		//事件监听
		SpringApplication application = new SpringApplication(SpringbootdemoApplication.class);
		//需要把监听器加入到spring容器中
		//application.addListeners(new ApplicationListenerTest());
		//Set<ApplicationListener<?>> listeners = application.getListeners();

	 	ConfigurableApplicationContext context =  application.run(args);
		//发布事件
		//context.publishEvent(new ApplicationEventTest(new Object()));


//@Import 测试
	 	//System.out.println(context.getBeansOfType(TomcatProperties.class));
		//System.out.println(context.getBean(TomcatProperties.class));
		System.out.println(context.getBean(Configdemo.class));
		//System.out.println(context.getBeansOfType(Configdemo.class));
		//System.out.println(context.getBeanFactory());





		//System.out.println(context.getBeansOfType(EncodingConvert.class));

		//System.out.println(context.getBean("jestAutoConfiguration"));

		//System.out.println(context.getEnvironment().getProperty("file.encoding"));

		context.close();

		//SpringApplication.run(SpringbootdemoApplication.class, args);
	}
}
