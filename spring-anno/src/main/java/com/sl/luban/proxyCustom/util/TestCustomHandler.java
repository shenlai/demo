package com.sl.luban.proxyCustom.util;

import com.sl.luban.proxyCustom.dao.CoustomInvocationHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestCustomHandler implements CoustomInvocationHandler {
    Object target;

    public TestCustomHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Method method) {
        try {
            System.out.println("TestCustomHandler.invoke");
            return method.invoke(target);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
