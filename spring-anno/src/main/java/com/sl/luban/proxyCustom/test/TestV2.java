package com.sl.luban.proxyCustom.test;

import com.sl.luban.proxyCustom.dao.LubanDao;
import com.sl.luban.proxyCustom.dao.LubanDaoImpl;
import com.sl.luban.proxyCustom.dao.UserDao;
import com.sl.luban.proxyCustom.dao.UserDaoImpl;
import com.sl.luban.proxyCustom.proxy.ProxyUtilV2;
import com.sl.luban.proxyCustom.util.LubanDaoInvocationHandler;
import com.sl.luban.proxyCustom.util.TestCustomHandler;
import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;
//import org.springframework.cglib.proxy.Proxy;


public class TestV2 {
    public static void main(String[] args) {

        //自定义动态代理
//        UserDao proxy = (UserDao) ProxyUtilV2.newInstance(UserDao.class, new TestCustomHandler(new UserDaoImpl()));
//        try {
//            proxy.query();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        //JDK 动态代理
//        LubanDao jdkProxy = (LubanDao) Proxy.newProxyInstance(Test.class.getClassLoader(),
//                new Class[]{LubanDao.class},
//                new LubanDaoInvocationHandler(new LubanDaoImpl()));


        byte[] bytes = ProxyGenerator.generateProxyClass("$Proxy18", new Class[]{LubanDao.class});
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("d:\\com\\google\\$Proxy18.class");
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //        System.out.println(proxy.proxy());
        LubanDao jdkproxy = (LubanDao) Proxy.newProxyInstance(Test.class.getClassLoader(),
                new Class[]{LubanDao.class}, new LubanDaoInvocationHandler(new LubanDaoImpl()));

        //jdkproxy.query();
        try {
            jdkproxy.query();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
