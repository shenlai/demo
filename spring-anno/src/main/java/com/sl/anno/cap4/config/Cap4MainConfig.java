package com.sl.anno.cap4.config;

import com.sl.anno.cap1.model.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class Cap4MainConfig {
    //给容器中注册一个bean, 类型为返回值的类型, 默认是单实例
    /*
     * 懒加载: 主要针对单实例bean,默认在容器启动的时候创建对象
     * 懒加载:容器启动时候不创建对象, 仅当第一次使用(获取)bean的时候才创建被初始化

     */
    @Lazy
    @Bean
    public Person person() {
        System.out.println("给容器中添加person.......");  //加@Lazy注解 创建容器时不创建bean, context.getbean时创建bean
        return new Person("james", 20);
    }
}
