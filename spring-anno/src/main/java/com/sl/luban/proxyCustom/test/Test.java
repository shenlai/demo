package com.sl.luban.proxyCustom.test;

import com.sl.luban.proxyCustom.dao.LubanDao;
import com.sl.luban.proxyCustom.dao.LubanDaoImpl;
import com.sl.luban.proxyCustom.proxy.ProxyUtil;

import java.lang.reflect.Proxy;
//import org.springframework.cglib.proxy.Proxy;


public class Test {
    public static void main(String[] args) {

        //自定义
        LubanDao proxy = (LubanDao) ProxyUtil.newInstance(new LubanDaoImpl());
        proxy.query();
        System.out.println("返回值：" + proxy.proxy("hello"));

        //JDK 代理类
        //LubanDao proxy2 = Proxy.newProxyInstance()


    }
}
