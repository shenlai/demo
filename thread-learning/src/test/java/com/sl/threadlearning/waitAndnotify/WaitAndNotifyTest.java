package com.sl.threadlearning.waitAndnotify;

import org.junit.Test;

/**
 * TODO 参考：https://www.cnblogs.com/xrq730/p/4853932.html
 * 线程同步中使用，即在synchronized块中（必须在锁中使用，不然释放什么呢？）
 * wait()  的作用是使当前执行代码的线程进行等待，【释放锁资源】，将当前线程置入"预执行队列"中，并且wait()所在的代码处停止执行，直到接到通知或被中断。
 * 在调用wait()之前，线程必须获得该对象的锁，因此只能在同步方法/同步代码块中调用wait()方法。
 * notify: 如果有多个线程等待，那么线程规划器随机挑选出一个wait的线程，对其发出通知notify()，并使它等待获取该对象的对象锁。注意"等待获取该对象的对象锁"，
 * 这意味着，即使收到了通知，wait的线程也不会马上获取对象锁，必须等待notify()方法的线程释放锁才可以。和wait()一样，notify()也要在同步方法/同步代码块中调用 【并不释放锁的资源,等待同步代码块执行完才释放】
 * ---
 * wait()、notify()、notifyAll()是三个定义在Object类里的方法，可以用来控制线程的状态。
 * 这三个方法最终调用的都是jvm级的native方法，随着jvm运行平台的不同可能有些许差异
 * 如果对象调用wait方法就会使持有该对象的线程把该对象的控制权交出去，然后处于等待状态
 * <p>
 * 如果兑对象调用notify方法，就会通知某个正在等待这个对象控制权的线程可以继续运行了（wait线程不一定会立即执行，必须等待notify同步代码块执行完成才可以执行）
 * <p>
 * 如果对象调用notifyAll方法，就会通知所有等待这个对象控制权的线程继续运行
 * 注意： 一定要在线程同步中使用，并且是同一个锁的资源
 * --
 * wait 和 sleep的区别
 * <p>
 * sleep方法属于Thread类的
 * wait 属于Object类
 * <p>
 * sleep方法使程序暂停执行一定的时间，让出CPU给其他线程，但是他的监控状态依然保持着，
 * 当指定时间到达后又会自动回复运行状态，调用sleep方法过程中，线程不会释放对象锁
 * <p>
 * wait方法，线程会释放对象锁，进入等待状态此对象的等待锁定池，只有针对此对象调用notify方法后，
 * 该线程才可能进入对象锁定池准备，获取对象锁才能继续运行
 * <p>
 * notifyAll()唤醒所有线程\唤醒的顺序不重要，因为notifyAll()把处于同一资源下wait的线程全部唤醒，至于唤醒的顺序，就和线程启动的顺序一样，是虚拟机随机的
 */
public class WaitAndNotifyTest {

    @Test
    public void testWaitAndNoitify() throws InterruptedException {
        Res res = new Res();
        InThrad intThrad = new InThrad(res);
        OutThread outThread = new OutThread(res);
        intThrad.start();
        outThread.start();

        Thread.sleep(10 * 60 * 1000);

    }


}
