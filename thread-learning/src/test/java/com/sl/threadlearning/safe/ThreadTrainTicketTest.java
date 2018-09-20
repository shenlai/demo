package com.sl.threadlearning.safe;

import org.junit.Test;

public class ThreadTrainTicketTest {

    /**
     * 测试线程同步
     * synchronized关键字
     * @throws InterruptedException
     */
    @Test
    public  void testThreadSafe() throws InterruptedException {

        ThreadTrainTicket threadTrain1 = new ThreadTrainTicket();
        Thread t1 = new Thread(threadTrain1, "①号窗口");
        Thread t2 = new Thread(threadTrain1, "②号窗口");
        Thread t3 = new Thread(threadTrain1, "③号窗口");
        t1.start();
        t2.start();
        t3.start();
        System.out.println("**********开售***********");
        Thread.sleep(3000);
    }


    /**
     * 测试 synchronized 函数使用 this锁
     *      synchronized 静态函数 使用 类的字节码文件作为锁
     */
    @Test
    public  void testThreadThisLock() throws InterruptedException {

        ThreadTrainTicket threadTrain1 = new ThreadTrainTicket();
        Thread t1 = new Thread(threadTrain1, "①号窗口");
        Thread t2 = new Thread(threadTrain1, "②号窗口");
        t1.start();
        Thread.sleep(40);
        threadTrain1.flag = false;//使t2线程走flag=true代码块，这时候t1线程呢？
        t2.start();
        System.out.println("**********开售***********");
        Thread.sleep(9000);
    }
}
