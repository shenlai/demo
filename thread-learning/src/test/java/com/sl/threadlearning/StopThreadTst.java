package com.sl.threadlearning;

import com.sl.threadlearning.create.StopThread;
import org.junit.Test;

/**
 * 停止线程思路
 *    1.  使用退出标志，使线程正常退出，也就是当run方法完成后线程终止。
 *     2.  使用stop方法强行终止线程（这个方法不推荐使用，因为stop和suspend、resume一样，也可能发生不可预料的结果）。
 *     3.  使用interrupt方法中断线程。
 */
public class StopThreadTst {

    @Test
    public void testStop1() throws InterruptedException {
        StopThread stopThread1 = new StopThread();
        Thread thread = new Thread(stopThread1);
        thread.start();

        for (int i=1;i<10;i++){
            System.out.println("我是主线程，i"+i);
            Thread.sleep(1000);
            if(i==6){
                stopThread1.stopThread();
                break;
            }
        }

        Thread.sleep(10*1000);
    }


    @Test
    public void testStop() {
        StopThread stopThread1 = new StopThread();
        Thread thread1 = new Thread(stopThread1);
        Thread thread2 = new Thread(stopThread1);
        thread1.start();
        thread2.start();
        int i = 0;
        while (true) {
            System.out.println("thread main..");
            if (i == 300) {
                // stopThread1.stopThread();
                thread1.interrupt();
                thread2.interrupt();
                break;
            }
            i++;
        }


    }
}
