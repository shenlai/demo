package com.sl.pool;

/**
 * @Author: sl
 * @Date: 2020/5/19
 * @Description: TODO
 */
public class WorkTest {
    public static void main(String[] args) {
        DefaultThreadPool defaultThreadPool = new DefaultThreadPool(10);
        for (int i = 0; i < 10000; i++) {

            Job job = new Job();
            defaultThreadPool.execute(job);
        }
    }
}
