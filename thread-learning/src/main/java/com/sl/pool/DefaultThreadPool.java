package com.sl.pool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: https://www.jianshu.com/p/d5d7035b6f26
 * @Date: 2020/5/19
 * 从线程池的实现中可以看出，当客户端调用execute(Job)方法时，会不断地向任务列表jobs中添加Job，而每个工作* 者线程会不读的从jobs上获取Job来执行，当jobs为空时，工作者线程进入WAITING状态。
 * @Description:
 */
public class DefaultThreadPool implements MyThreadPool {

    /**
     * 线程池维护最大工作者线程数
     */
    private static final int MAX_WORKER_NUM = 30;

    /**
     * 线程池维护工作者线程默认数量
     */
    private static final int DEFAULT_WORKER_NUM = 5;

    /**
     * 线程池维护工作者线程最小个数
     */
    private static final int MIN_WORKER_NUM = 1;

    /**
     * 维护一个任务列表，里面加入客户端发起的任务
     */
    private final LinkedList<Runnable> jobs = new LinkedList<>();


    private List<MyWorker> workers = new ArrayList<>();

    /**
     * 线程池开启的线程数量
     */
    private int wokerNum;


    /**
     * 没个线程编号生成
     */
    private AtomicLong threadNum = new AtomicLong();


    public DefaultThreadPool(int workerNum) {
        if (wokerNum > MAX_WORKER_NUM) {
            this.wokerNum = MAX_WORKER_NUM;
        } else {
            this.wokerNum = workerNum;
        }
        initThreadPool();
    }

    /**
     * 初始化每个工作者线程
     */
    private void initThreadPool() {
        for (int i = 0; i < this.wokerNum; i++) {
            MyWorker worker = new MyWorker();
            //添加到工作者线程列表
            workers.add(worker);
            //启动线程
            Thread thread = new Thread(worker);
            thread.start();
        }
    }


    /**
     * 执行一个任务job,job 必须实现runaable
     *
     * @param job
     */
    @Override
    public void execute(Runnable job) {
        //如果job为null，抛异常
        if (job == null) {
            throw new NullPointerException();
        }

        //根据线程的等待/通知机制， 必须对jobs枷锁
        synchronized (jobs) {
            jobs.addLast(job);
            jobs.notify();
        }

    }

    @Override
    public void shutdown() {
        for (MyWorker worker : workers) {
            worker.shutdown();
        }
    }


    class MyWorker implements Runnable {

        //标识是否运行该worker
        private volatile boolean running = true;


        @Override
        public void run() {
            while (running) {
                Runnable task = null;
                //线程的等待/通知机制
                synchronized (jobs) {
                    if (jobs.isEmpty()) {
                        try {
                            jobs.wait(); //线程等待唤醒
                        } catch (InterruptedException e) {
                            //感知到外部对该线程的中端操作，返回
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    //取出一个task
                    task = jobs.removeFirst();
                }
                //执行task
                if (task != null) {
                    task.run();
                }
            }
        }


        /**
         * 终止该线程
         */
        public void shutdown() {
            running = false;
        }

    }
}
