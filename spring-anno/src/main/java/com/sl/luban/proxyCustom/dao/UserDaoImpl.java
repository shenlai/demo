package com.sl.luban.proxyCustom.dao;

public class UserDaoImpl implements UserDao {

    public void query() throws Exception {
        System.out.println("执行目标方法： query");
    }

    @Override
    public String query2() throws Exception {
        System.out.println("执行目标方法：query2");
        return "query2";
    }


}
