package com.sl.threadlearning.lockLearn;

import org.junit.Test;

import java.util.concurrent.locks.Condition;

/**
 *
 * Lock接口与synchronized关键字的区别
 *
 * Lock接口可以尝试非阻塞的获取锁，当前线程尝试获取锁，如果这一时刻锁没哟被其他线程获取到。则成功获取并持有锁
 * Lock接口能被中断的获取锁 与syncjronized不同，获取到的锁的线程能够响应中断，当获取到的锁的线程被中断时，中断异常将被排除，同时锁会被释放
 *
 * Lock接口在指定的截止时间之前获取锁，如果截止时间到了依旧无法获取锁，则返回
 *
 *  synchronized在程序执行完毕或者抛出异常时自动释放锁
 *
 *  Condition接口
 *  Condition接口的功能类似于在传统的线程技术中Object.wait()和Object.notify()的功能
 *  Condition condition = lock.newCondition();
 *  res.condition.await();  类似wait
 *  res.condition.signal() 类似notify
 *
 *  注意：必须使用同一把锁的condition对象 condition = lock.newCondition
 *
 *
 *
 */
public class LockTest {

    @Test
    public  void testLockAndCondition() throws InterruptedException {

        ResT res = new ResT();
        Condition newCondition = res.lock.newCondition();
        InThreadT inputThread = new InThreadT(res,newCondition);
        OutThreadT outThrad = new OutThreadT(res,newCondition);
        inputThread.start();
        outThrad.start();

        Thread.sleep(1000);
    }
}
