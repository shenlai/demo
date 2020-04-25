package com.sl.zklock;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DistrbuteLockTest {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    //countDownLatch.await();
                    DistributeLock lock = new DistributeLock();
                    lock.lock();
                    TimeUnit.SECONDS.sleep(5);
                    lock.unlock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "thread--" + i).start();
           //countDownLatch.countDown();
        }
    }
}



