package com.sl.luban.proxyCustom.test;

import com.sl.luban.proxyCustom.dao.LubanDao;
import com.sl.luban.proxyCustom.dao.LubanDaoImpl;
import com.sl.luban.proxyCustom.util.LubanDaoInvocationHandler;

import java.lang.reflect.Proxy;
//import org.springframework.cglib.proxy.Proxy;


public class Test {
    public static void main(String[] args) {

        //自定义动态代理
//        LubanDao proxy = (LubanDao) ProxyUtil.newInstance(new LubanDaoImpl());
//        proxy.query();
//        System.out.println("返回值：" + proxy.proxy("hello"));


        //JDK 动态代理

        /**
         * JDK实际生成代理类
         * public class $Proxy implements LubanDao{
         *
         *      private LubanDaoInvocationHandler h;
         *      private LubanDao target;
         *
         *      public $Proxy (LubanDao target){
         *          this.target =target;
         *      }
         *
         *      public void query() {
         *          return h.invoke();
         *      }
         * }
         */
        LubanDao jdkProxy = (LubanDao) Proxy.newProxyInstance(Test.class.getClassLoader(),
                new Class[]{LubanDao.class},
                new LubanDaoInvocationHandler(new LubanDaoImpl()));
        jdkProxy.query();

    }
}
