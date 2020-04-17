package com.sl.anno.cap9.service;

import com.sl.anno.cap9.dao.TestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TestService {

    //@Qualifier("testDao")//指定名称来加载
    @Autowired
    //@Resource
    private TestDao testDao;

    //如果使用Autowired, testDao2,优先按beanID找，找不到按类型找 找到TestDao类型的
    // 如果存在 @Primary 则直接取@Primary修饰的bean
    //@Qualifier, @Promary 同时存在，优先@Qualifier按名称找
    public void println() {
        System.out.println("Service...dao...." + testDao);
    }
}
