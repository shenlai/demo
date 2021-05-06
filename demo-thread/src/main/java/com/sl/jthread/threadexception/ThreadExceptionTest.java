package com.sl.jthread.threadexception;

/**
 * @Author: sl
 * @Date: 2019/3/14
 * @Description: TODO
 */
public class ThreadExceptionTest extends Thread {

    @Override
    public void run() {
        System.out.println("begin handle business");
        int res = 10/0;
        System.out.println("end handle business");
    }


}
