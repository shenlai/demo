package com.sl.springlearning;

import com.sl.springlearning.aop.ICaculator;
import com.sl.springlearning.aop.MathCaculator;
import com.sl.springlearning.aop.MathCaculatorV2;
import com.sl.springlearning.config.MainConfigOfAop;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AopTest {

    @Test
    public void test01() {
        //创建IOC容器,注入单实例bean
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAop.class);

        //1. 这种方式不会有拦截功能，原因：AOP通过代理实现
        //MathCaculator cal = new MathCaculator();
        //cal.div(20,5);

        //2. 从容器中获取MathCaculator
//        MathCaculator cal = applicationContext.getBean(MathCaculator.class);
//        cal.div(10, 0);  //CGLIB

        //2. 从容器中获取MathCaculator
        ICaculator calv2 = (ICaculator) applicationContext.getBean("calculatorV2");
        ICaculator calv21 = applicationContext.getBean(ICaculator.class);
        calv2.div(10, 2);  //JDK

        /*
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
        */

        applicationContext.close();
    }

}
