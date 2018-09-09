package com.sl.springlearning;

import com.sl.springlearning.config.LifeCycleConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public class LifeCycleTest {


    @Test
    public  void test01(){
        //创建IOC容器,注入单实例bean
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        System.out.println("容器创建完成");
        applicationContext.close();
    }
}
