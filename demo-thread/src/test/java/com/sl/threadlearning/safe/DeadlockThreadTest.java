package com.sl.threadlearning.safe;

import org.junit.Test;

public class DeadlockThreadTest {

    /**
     *  模拟死锁
     *  线程1：先拿到mutex锁，再拿到this锁
     *  线程2：先拿到this锁，再拿到mutex锁
     *  死锁产生的原因： 同步中嵌套同步，互相等待对方释放
     *
     */
    @Test
    public  void testDeadLock() throws InterruptedException {

        ThreadDeadLock threadTrain = new ThreadDeadLock(); // 定义 一个实例
        Thread thread1 = new Thread(threadTrain, "一号窗口");
        Thread thread2 = new Thread(threadTrain, "二号窗口");
        thread1.start();
        Thread.sleep(40);
        threadTrain.flag = false;
        thread2.start();

        Thread.sleep(10000);
    }
}
