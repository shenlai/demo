package com.sl.luban.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ScheduledDemo {

    public static void main(String[] args) {
        //创建一个线程池，可以安排在给定延迟后运行命令或者定期执行
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 10; i++) {
            Runnable task = new TaskDemo();
            //把任务交给pool去执行
            pool.execute(task);
        }
    }
}
