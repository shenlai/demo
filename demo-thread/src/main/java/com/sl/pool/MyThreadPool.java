package com.sl.pool;

/**
 * @Author: sl
 * @Date: 2020/5/19
 * @Description: TODO
 */
public interface MyThreadPool {
    /**
     * 执行一个任务
     *
     * @param job
     */
    void execute(Runnable job);

    /**
     * 关闭线程池
     */
    void shutdown();






}
