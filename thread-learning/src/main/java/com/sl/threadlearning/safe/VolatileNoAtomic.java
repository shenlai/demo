package com.sl.threadlearning.safe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile与synchronized区别
 * 仅靠volatile不能保证线程的安全性。（原子性）
 * ①volatile轻量级，只能修饰变量。synchronized重量级，还可修饰方法
 * ②volatile只能保证数据的可见性，不能用来同步，因为多个线程并发访问volatile修饰的变量不会阻塞。
 * synchronized不仅保证可见性，而且还保证原子性，因为，只有获得了锁的线程才能进入临界区，从而保证临界区中的所有语句都全部执行。多个线程争抢synchronized锁对象时，会出现阻塞。
 * 线程安全性
 * 线程安全性包括两个方面，①可见性。②原子性。
 * 从上面自增的例子中可以看出：仅仅使用volatile并不能保证线程安全性。而synchronized则可实现线程的安全性。
 */
public class VolatileNoAtomic implements Runnable {


    //volatile 变量在多个线程之间可见，但是并不保证线程安全
    //private volatile static int count = 0;
    //static 修饰变量，存在静态区，只会存放一次，所有线程共享
    //private static int count = 0;
    public volatile static int count = 0;
    //保证操作原子性
    //private  static AtomicInteger atomicCount = new AtomicInteger(0);

    private void addCount() throws InterruptedException {
        //Thread.sleep(500);
        for (int i = 0; i < 1000; i++) {
            count++;
            //atomicCount.incrementAndGet();
        }
        System.out.println("线程：" + Thread.currentThread().getName() + "输出：" + count);
        //System.out.println("线程："+getName()+"输出："+ atomicCount);
    }

    @Override
    public void run() {
        try {
            //addCount()
            Thread.sleep(100);
            for (int i = 0; i < 1000; i++) {
                count++;
                //atomicCount.incrementAndGet();
            }
            System.out.println("线程：" + Thread.currentThread().getName() + "输出：" + count);
            //System.out.println("线程："+getName()+"输出："+ atomicCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
