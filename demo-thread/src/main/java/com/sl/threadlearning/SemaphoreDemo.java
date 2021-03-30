package com.sl.threadlearning;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo implements Runnable {

    private String name;

    private Semaphore semaphore;

    public  SemaphoreDemo(String name,Semaphore wc){
        this.name = name;
        this.semaphore = wc;
    }

    @Override
    public void run(){

        try{
            //剩余资源
            int avaiablePermits = this.semaphore.availablePermits();
            if(avaiablePermits>0){
                System.out.println(name+"：有资源了，可以访问");
            }else{
                System.out.println(name+"：继续等待");
            }
            //申请资源，申请不到则等待
            semaphore.acquire();
            System.out.println(name+"有权限，执行逻辑");
            Thread.sleep(new Random().nextInt(1000)); // 模拟处理业务时间。
            System.out.println(name+"执行完成，释放...");
            semaphore.release();
        }catch (Exception e){

        }

    }



}
