package com.sl.springbootdemo.EnableAnnotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

//https://www.cnblogs.com/leihuazhe/p/7743479.html
@SpringBootApplication

@ComponentScan
//@Import(User.class)   相当于User类上加注解@Component
//@Import() 用来导入一个或多个类（会被Spring容器托管），导入配置类（配置类里的Bean会被Spring容器托管）
//@Import(MyImportSelector.class)

@EnableLog(name="springboot demo")
public class SpringbootdemoApplication2 {

	public static void main(String[] args) {
	 	ConfigurableApplicationContext context = SpringApplication.run(SpringbootdemoApplication2.class,args);

	 	System.out.println(context.getBeansOfType(User.class)); //context.getBean(User.class)  获取不到则报错
		System.out.println(context.getBeansOfType(Role.class));





		//System.out.println(context.getBeansOfType(EncodingConvert.class));

		//System.out.println(context.getBean("jestAutoConfiguration"));

		//System.out.println(context.getEnvironment().getProperty("file.encoding"));

		//context.close();

		//SpringApplication.run(SpringbootdemoApplication.class, args);
	}
}
