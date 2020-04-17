package com.sl.anno.cap6.config;

import com.sl.anno.cap6.bean.Monkey;
import org.springframework.beans.factory.FactoryBean;


//@Compont等待扫描   或config类中以@Bean方式注入
//注入的beanname 是 "JamesFactoryBean" 实例对象是 Monkey
public class JamesFactoryBean implements FactoryBean<Monkey> {

    @Override
    public Monkey getObject() throws Exception {
        // TODO Auto-generated method stub
        return new Monkey();
    }

    @Override
    public Class<?> getObjectType() {
        // TODO Auto-generated method stub
        return Monkey.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
