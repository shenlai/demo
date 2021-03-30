package com.sl.luban.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedPool {

    public static void main(String[] args) {
        //创建可变大小线程池
        ExecutorService pool=Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
                //创建任务
                Runnable task=new TaskDemo();
                //把任务交给pool去执行
                pool.execute(task);
        }

    }
}
