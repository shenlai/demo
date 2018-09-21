package com.sl.threadlearning;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;


/**
 *
 * CountDownLatch是一个同步工具类，用来协调多个线程之间的同步，或者说起到线程之间的通信（而不是用作互斥的作用）。
 * CountDownLatch能够使一个线程在等待另外一些线程完成各自工作之后，再继续执行。使用一个计数器进行实现。计数器初始值为线程的数量。
 * 当每一个线程完成自己任务后，计数器的值就会减一。当计数器的值为0时，表示所有的线程都已经完成了任务，然后在CountDownLatch上等待的线程就可以恢复执行任务。
 *
 *
 *  实现原理：https://blog.csdn.net/yanyan19880509/article/details/52349056
 */
public class CountDownLatchTest {

    @Test
    public void testCountDownLatch() throws InterruptedException {
        System.out.println("等待子线程执行完毕...");
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程," + Thread.currentThread().getName() + "开始执行...");
                countDownLatch.countDown();// 每次减去1
                System.out.println("子线程," + Thread.currentThread().getName() + "..执行后countDownLatch数值：" + countDownLatch.getCount());
                System.out.println("子线程," + Thread.currentThread().getName() + "结束执行...");
            }
        }).start();
        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("子线程," + Thread.currentThread().getName() + "开始执行...");
                countDownLatch.countDown();
                System.out.println("子线程," + Thread.currentThread().getName() + "..执行后countDownLatch数值：" + countDownLatch.getCount());
                System.out.println("子线程," + Thread.currentThread().getName() + "结束执行...");
            }
        }).start();
        System.out.println("countDownLatch数值：" + countDownLatch.getCount());
        countDownLatch.await();// 调用当前方法主线程阻塞  countDown结果为0, 阻塞变为运行状态
        System.out.println("两个子线程执行完毕....");
        System.out.println("继续主线程执行..");


    }
}
