package com.sl.springbootdemo.bootflow;

import com.sl.springbootdemo.EnableAnnotation.Role;
import com.sl.springbootdemo.EnableAnnotation.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		//方式1 直接调用静态方法  内部转换成第二中
	 	ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class,args);

		//方式2
		//SpringApplication app = new SpringApplication(DemoApplication.class);
		//app.run(args);
	}
}
