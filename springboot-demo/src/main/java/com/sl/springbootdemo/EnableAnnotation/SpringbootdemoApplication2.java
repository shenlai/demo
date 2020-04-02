package com.sl.springbootdemo.EnableAnnotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

//https://www.cnblogs.com/leihuazhe/p/7743479.html
//@SpringBootApplication
//@ComponentScan
//@EnableAutoConfiguration
//@Import(User.class)   //相当于User类上加注解@Component
//@Import() 用来导入一个或多个类（会被Spring容器托管），导入配置类（配置类里的Bean会被Spring容器托管）
//@Import(MyImportSelector.class)
//@EnableLog(name="springboot demo")


@Import({ImportByConfiguration.class, //导入bean配置类，则配置类中bean也将注入到spring容器
        ImportSelectorTest.class,     //ImportSelector接口方式
        ImportBeanDefinitionRegistrarTest.class  //ImportBeanDefinitionRegistrar接口方式
})
//@ComponentScan
@SpringBootApplication
public class SpringbootdemoApplication2 {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootdemoApplication2.class, args);

        System.out.println(context.getBeansOfType(ImportTestClass.class));
        System.out.println(context.getBeansOfType(Role.class));


        //System.out.println(context.getBeansOfType(EncodingConvert.class));

        //System.out.println(context.getBean("jestAutoConfiguration"));

        //System.out.println(context.getEnvironment().getProperty("file.encoding"));

        //context.close();

        //SpringApplication.run(SpringbootdemoApplication.class, args);
    }
}
