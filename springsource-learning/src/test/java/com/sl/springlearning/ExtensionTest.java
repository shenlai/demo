package com.sl.springlearning;

import com.sl.springlearning.extension.ExtensionConfig;
import com.sl.springlearning.extension.MyApplicationEvent;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ExtensionTest {

    @Test
    public  void test01(){

        AnnotationConfigApplicationContext applicationContext  = new AnnotationConfigApplicationContext(ExtensionConfig.class);
        applicationContext.publishEvent(new MyApplicationEvent(this,"自定义事件"));

        applicationContext.close();

    }
}
