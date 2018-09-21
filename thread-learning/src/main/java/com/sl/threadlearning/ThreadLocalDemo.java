package com.sl.threadlearning;

public class ThreadLocalDemo  extends  Thread{

    private  ThreadLocalRes threadLocalRes;

    public  ThreadLocalDemo(ThreadLocalRes res){
        this.threadLocalRes = res;
    }


    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println(Thread.currentThread().getName() + "---" + "i---" + i + "--num:" + threadLocalRes.getCount());
        }

    }
}
