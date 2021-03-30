package com.sl.luban;

import java.util.concurrent.TimeUnit;

public class VolatileUpdater {

    final static int MAX = 50;

    //static volatile int init_value = 0;
    static int init_value=0;

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 100000; i++) {
                init_value++;
                System.out.println("updater1:" + (init_value));
            }
        }, "Reader1").start();

        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            for (int i = 0; i < 100000; i++) {
                init_value++;
                System.out.println("updater2:" + (init_value));
            }

        }, "Reader2").start();


        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("result:" + (init_value));
    }
}
