package com.sl.threadlearning;

public class ThreadLocalRes {

    // 生成序列号共享变量
    public static Integer count = 0;

    public static  ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        //不给初始值，会报空指针异常
        protected Integer initialValue() {
            return 0;
        };

    };

    public  Integer getCount(){
        int count = threadLocal.get()+1;
        threadLocal.set(count);
        return  count;
    }
}


