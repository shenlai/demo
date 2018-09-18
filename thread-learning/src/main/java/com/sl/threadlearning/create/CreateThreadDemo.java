package com.sl.threadlearning.create;


/**
 * 创建线程有哪些方式：
 * 1. 使用继承Thread类方式，继承Thread，重写run
 * 2. 实现runable接口方式（继承Thread类也是实现runable接口）
 * 3. 使用匿名内部类方式
 * 4. callable
 * 5. 使用线程池创建线程
 */
public class CreateThreadDemo  extends   Thread {
    /**
     * run方法就是线程需要执行的任务或者执行的代码
     */
    @Override
    public void run() {
        for (int i = 0; i < 30; i++) {
            System.out.println("run,i:" + i+"  线程ID:"+getId());
        }

    }
}
