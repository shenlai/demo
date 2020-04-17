package com.sl.anno.cap7.config;

import com.sl.anno.cap1.model.Person;
import com.sl.anno.cap7.bean.Bike;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@ComponentScan("com.sl.anno.cap7.bean")
@Configuration
public class Cap7MainConfigOfLifeCycle {

    @Bean("person")
    public Person person() {
        System.out.println("给容器中添加person.......");
        return new Person("person", 20);
    }

    /**
     * 通过注解的方式指定初始化和销毁方法
     * 执行时机？？
     *
     * @return
     */
    //@Scope("prototype")  //多实例bean 只有在获取bean的时候才初始化, 容器只负责初始化，容器关闭时不会调用销毁方法
    @Bean(initMethod = "init", destroyMethod = "destory")
    public Bike bike() {
        return new Bike();
    }
}
