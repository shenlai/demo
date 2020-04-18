package com.sl.luban.proxyCustom.dao;

import java.lang.reflect.Method;

public interface CoustomInvocationHandler {
    public Object invoke(Method method);
}
