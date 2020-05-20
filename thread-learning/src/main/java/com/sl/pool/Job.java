package com.sl.pool;

/**
 * @Author: sl
 * @Date: 2020/5/19
 * @Description: TODO
 */
public class Job implements Runnable {
    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("当前线程名称:" + Thread.currentThread().getName() + ";" + "job被指执行了");
    }
}
