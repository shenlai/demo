package com.sl.threadlearning.pool;

import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    /**
     *  线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程
     *  重复利用
     */
    @Test
    public void testCacheThreadPool() throws InterruptedException {

        ExecutorService newCacheThreadPool = Executors.newCachedThreadPool();
        System.out.println("可缓存的线程池：");
        for(int i=0;i<10;i++){
            int temp = i;
            newCacheThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名："+Thread.currentThread().getName()+",i:"+temp);
                }
            });
        }
        Thread.sleep(1000);
    }

    @Test
    public void testFixedThreadPool() throws InterruptedException {
        // 2.可固定长度线程池
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(3);
        System.out.println("固定容量线程池：");
        for(int i=0;i<10;i++){
            int temp = i;
            newFixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名："+Thread.currentThread().getName()+",i:"+temp);
                }
            });
        }
        Thread.sleep(1000);
    }


    @Test
    public void testScheduledThreadPool() throws InterruptedException {
        //  可定时线程池
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(3);
        System.out.println("可定时线程池：");
        for(int i=0;i<10;i++){
            int temp = i;
            newScheduledThreadPool.schedule(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程名："+Thread.currentThread().getName()+",i:"+temp);
                }
            },3,TimeUnit.SECONDS); //延迟3秒执行
        }
        Thread.sleep(4000);
    }

    /**
     * 单线程
     */
    @Test
    public void testSingleThreadScheduleExecutor() throws InterruptedException {

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        for(int i=0;i<10;i++){
            int temp= i;
            scheduledExecutorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        int j =1/0;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + ",i:" + temp);
                }
            });
        }
        scheduledExecutorService.shutdown();
        //Thread.sleep(4000);
    }







}
