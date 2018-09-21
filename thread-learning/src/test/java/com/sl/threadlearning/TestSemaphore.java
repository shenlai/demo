package com.sl.threadlearning;

import org.junit.Test;

import java.util.concurrent.Semaphore;

/**
 * Semaphore分为单值和多值两种，前者只能被一个线程获得，后者可以被若干个线程获得。
 * Semaphore当前在多线程环境下被扩放使用，操作系统的信号量是个很重要的概念，在进程控制方面都有应用。
 * Java并发库Semaphore 可以很轻松完成信号量控制，Semaphore可以控制某个资源可被同时访问的个数，
 * 通过 acquire() 获取一个许可，如果没有就等待，而 release() 释放一个许可。
 *
 * 　单个信号量的Semaphore对象可以实现互斥锁的功能，
 *   并且可以是由一个线程获得了“锁”，再由另一个线程释放“锁”，这可应用于死锁恢复的一些场合。
 */
public class TestSemaphore {

    @Test
    public  void testSemaphore() throws InterruptedException {
        // 最多允许3个线程访问
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <=10; i++) {
            SemaphoreDemo parent = new SemaphoreDemo("第"+i+"线程,",semaphore);
            new Thread(parent).start();
        }

        Thread.sleep(15*1000);
    }
}
