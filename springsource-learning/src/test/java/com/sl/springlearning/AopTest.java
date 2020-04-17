package com.sl.springlearning;

import com.sl.springlearning.aop.MathCaculator;
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
        MathCaculator cal = applicationContext.getBean(MathCaculator.class);
        cal.div(10, 0);

        /*
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
        */

        applicationContext.close();
    }

}
