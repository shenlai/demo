package com.sl.luban.proxyCustom.dao;

public class LubanDaoImpl implements LubanDao {

    @Override
    public void query() {
        System.out.println("执行实际逻辑");

    }

    @Override
    public String proxy(String msg) {
        System.out.println("execute proxy method");
        return "proxy:" + msg;
    }


}
