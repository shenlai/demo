package com.sl.threadlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ThreadLearningApplication {

    public static void main(String[] args) throws InterruptedException {
        //SpringApplication.run(ThreadLearningApplication.class, args);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 30; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                    System.out.println("我是子线程...ID" + Thread.currentThread().getId() + "   当前运行index:" + i);
                }
            }
        });

        thread.start();
        //Thread.sleep(1000);
        System.out.println("主线程是守护线程？：" + Thread.currentThread().isDaemon());
        System.out.println("主线程执行完毕!");

        //强行退出线程
        //System.exit(0);
        System.out.println("主线程执行完毕!");
    }
}
