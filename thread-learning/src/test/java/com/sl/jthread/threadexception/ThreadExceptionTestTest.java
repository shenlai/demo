package com.sl.jthread.threadexception;

import org.junit.Test;

/**
 *
 * 方法1. 创建线程时设置异常处理Handler
 *
 *
 *
 */

public class ThreadExceptionTestTest {

    @Test
    public void testThreadThrowException() throws InterruptedException {

        try{
            ThreadExceptionTest t1 = new ThreadExceptionTest();

            t1.start(); //
            t1.setUncaughtExceptionHandler(new MyUnchecckedExceptionhandler());
            for (int i = 0; i < 30; i++) {
                System.out.println("main,i:" + i);
            }

        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }

        Thread.sleep(1 * 1000 * 10);

    }


}