package com.sl.luban.proxyCustom.test;

import com.sl.luban.proxyCustom.dao.LubanDao;
import com.sl.luban.proxyCustom.dao.LubanDaoImpl;
import com.sl.luban.proxyCustom.proxy.ProxyUtil;


public class Test {
    public static void main(String[] args) {

        LubanDao proxy = (LubanDao) ProxyUtil.newInstance(new LubanDaoImpl());
        proxy.query();


    }
}
