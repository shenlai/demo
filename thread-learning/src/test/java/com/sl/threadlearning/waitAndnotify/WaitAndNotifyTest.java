package com.sl.threadlearning.waitAndnotify;

import org.junit.Test;

/**
 * 线程同步中使用，即在synchronized块中（必须在锁中使用，不然释放什么呢？）
 *  wait作用：让当前线程从运行状态变成休眠状态（等待状态），释放锁的资源
 *  notify: 让线程从休眠状态变为运行状态,并不释放锁的资源,等待同步代码块执行完才释放
 *
 * wait()、notify()、notifyAll()是三个定义在Object类里的方法，可以用来控制线程的状态。
 * 这三个方法最终调用的都是jvm级的native方法，随着jvm运行平台的不同可能有些许差异
 * 如果对象调用wait方法就会使持有该对象的线程把该对象的控制权交出去，然后处于等待状态
 *
 * 如果兑对象调用notify方法，就会通知某个正在等待这个对象控制权的线程可以继续运行了（wait线程不一定会立即执行，必须等待notify同步代码块执行完成才可以执行）
 *
 * 如果对象调用notifyAll方法，就会通知所有等待这个对象控制权的线程继续运行
 * 注意： 一定要在线程同步中使用，并且是同一个锁的资源
 *
 * wait 和 sleep的区别
 *
 * sleep方法属于Thread类的
 * wait 属于Object类
 *
 * sleep方法使程序暂停执行一定的时间，让出CPU给其他线程，但是他的监控状态依然保持着，
 *   当指定时间到达后又会自动回复运行状态，调用sleep方法过程中，线程不会释放对象锁
 *
 *  wait方法，线程会释放对象锁，进入等待状态此对象的等待锁定池，只有针对此对象调用notify方法后，
 *  该线程才可能进入对象锁定池准备，获取对象锁才能继续运行
 *
 */
public class WaitAndNotifyTest {

    @Test
    public  void testWaitAndNoitify() throws InterruptedException {
        Res res = new Res();
        InThrad intThrad = new InThrad(res);
        OutThread outThread = new OutThread(res);
        intThrad.start();
        outThread.start();

        Thread.sleep(100);

    }

}
