package com.sl.threadlearning;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier是java.util.concurrent包下面的一个工具类，字面意思是可循环使用（Cyclic）的屏障（Barrier），
 * 通过它可以实现让一组线程到达一个屏障（也可以叫同步点）时被阻塞，直到最后一个线程到达屏障时，所有被屏障拦截的线程才会继续执行。
 */
public class WriterUtil extends  Thread {

    private CyclicBarrier cyclicBarrier;

    public  WriterUtil(CyclicBarrier cyclicBarrier){
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        System.out.println("线程" + Thread.currentThread().getName() + ",正在写入数据");

        //写了一段时间
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            // TODO: handle exception
        }
        System.out.println("线程" + Thread.currentThread().getName() + ",写入数据成功.....");

        try {
            cyclicBarrier.await();
        } catch (Exception e) {
        }
        System.out.println("所有线程执行完毕..........");
    }
}
