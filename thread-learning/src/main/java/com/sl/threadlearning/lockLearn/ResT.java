package com.sl.threadlearning.lockLearn;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁
 * https://www.cnblogs.com/xrq730/p/4855155.html
 */
public class ResT {

    public String userSex;
    public String userName;

    //线程通讯标识，true:消费者可以消费，生产者线程等待，
    //              false:生产者线程可以生产， 消费者等待
    // 默认为false：先执行生产者线程
    public boolean flag = false;

    public Lock lock = new ReentrantLock();
}
