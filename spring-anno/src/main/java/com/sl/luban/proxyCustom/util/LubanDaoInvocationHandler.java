package com.sl.luban.proxyCustom.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;



public class LubanDaoInvocationHandler implements InvocationHandler {


    /**
     * 目标对象
     */
    private Object target;


    public LubanDaoInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * @param proxy  代理对象
     * @param method 目标方法
     * @param args   目标方法参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("execute LubanDaoInvocationHadler jdk proxy");
        return method.invoke(target, args);
    }
}
