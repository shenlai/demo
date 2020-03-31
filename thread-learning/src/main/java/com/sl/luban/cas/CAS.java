package com.sl.luban.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class CAS {

    private static volatile int m = 0;

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void increase() {
        m++;
    }

    public static void increase2() {

        atomicInteger.incrementAndGet();
    }


    public static void main(String[] args) throws InterruptedException {

        Thread[] t = new Thread[20];
        for (int i = 0; i < 20; i++) {

            t[i] = new Thread(() -> {
                CAS.increase();

            }, "Reader1");
            t[i].start();
            t[i].join();  //join方法加入

        }

        System.out.println(m);

        Thread[] tf = new Thread[20];
         for (int i = 0; i < 20; i++) {

            tf[i] = new Thread(() -> {
                CAS.increase2();

            }, "Reader2");
            tf[i].start();
            tf[i].join();

        }

        System.out.println("atomic:" + atomicInteger.get());


    }

}
