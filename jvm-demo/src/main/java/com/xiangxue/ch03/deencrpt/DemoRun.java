package com.xiangxue.ch03.deencrpt;

/**
 * 动脑学院-Mark老师
 * 创建日期：2017/08/31
 * 创建时间: 14:39
 */
public class DemoRun {

    public static void main(String[] args) throws Exception {
        DemoCustomClassLoader demoCustomClassLoader = new DemoCustomClassLoader("My ClassLoader");
        demoCustomClassLoader.setBasePath("D:\\Java_IDEA_Project\\springbootdemo\\jvm-demo\\doc\\");
        Class<?> clazz = demoCustomClassLoader.loadClass("DemoUser");
        System.out.println(clazz.getClassLoader());
        Object o = clazz.newInstance();
    }
}
