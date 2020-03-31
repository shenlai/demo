package com.sl.luban.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class CAS2 {

    private static volatile int m = 0;

    private static AtomicInteger atomicInteger = new AtomicInteger(100);

    //解决ABA问题=> 通过版本号
    private static AtomicStampedReference asr = new AtomicStampedReference(100, 1);

    public static void main(String[] args) throws InterruptedException {


        Thread t1 = new Thread(() -> {

            //带版本号
            //asr.compareAndSet(100, 110,1,20);

            //atomicInteger.compareAndSet(100, 110);
            System.out.println(atomicInteger.compareAndSet(100, 110));
            System.out.println(atomicInteger.get());
        });
        t1.start();


        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //atomicInteger.compareAndSet(110, 100);
            System.out.println(atomicInteger.compareAndSet(110, 100));

            System.out.println(atomicInteger.get());
        });
        t2.start();

        Thread t3 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //atomicInteger.compareAndSet(100, 120);
            System.out.println(atomicInteger.compareAndSet(100, 120));
            System.out.println(atomicInteger.get());
        });
        t3.start();


        System.out.println("atomic:" + atomicInteger.get());


    }

}
