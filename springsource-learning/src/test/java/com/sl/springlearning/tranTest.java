package com.sl.springlearning;

import com.sl.springlearning.tran.ProductServiceA;
import com.sl.springlearning.tran.txConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class tranTest {
    @Test
    public void test01() {
        //创建IOC容器,注入单实例bean
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(txConfig.class);

        ProductServiceA productServiceA = applicationContext.getBean(ProductServiceA.class);

        productServiceA.A();

        applicationContext.close();
    }

}
