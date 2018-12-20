package com.sl.springbootdemo.eac;

import com.sl.springbootdemo.EnableAnnotation.EnableLog;
import com.sl.springbootdemo.EnableAnnotation.Role;
import com.sl.springbootdemo.EnableAnnotation.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

//https://www.cnblogs.com/leihuazhe/p/7743479.html
//@SpringBootApplication

@ComponentScan
@Import(User.class)   //相当于User类上加注解@Component
//@Import() 用来导入一个或多个类（会被Spring容器托管），导入配置类（配置类里的Bean会被Spring容器托管）
//@Import(MyImportSelector.class)

@EnableLog(name="springboot demo")
public class SpringbootdemoApplication3 {

	public static void main(String[] args) {
	 	ConfigurableApplicationContext context = SpringApplication.run(SpringbootdemoApplication3.class,args);

	 	System.out.println(context.getBeansOfType(User.class)); //context.getBean(User.class)  获取不到则报错
		//System.out.println(context.getBeansOfType(Role.class));

		//SpringApplication app = new SpringApplication();
		//app.run(args);

		//SpringApplication.run(SpringbootdemoApplication3.class, args);
	}
}
