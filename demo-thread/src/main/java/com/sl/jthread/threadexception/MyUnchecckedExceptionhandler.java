package com.sl.jthread.threadexception;

/**
 * @Author: sl
 * @Date: 2019/3/15
 * @Description: TODO
 */
public class MyUnchecckedExceptionhandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("捕获异常处理方法：" + e);
    }
}
