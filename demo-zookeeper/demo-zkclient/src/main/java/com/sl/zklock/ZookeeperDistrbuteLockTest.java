package com.sl.zklock;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ZookeeperDistrbuteLockTest implements Runnable {

    //private ILock lock = new ZookeeperDistrbuteLock();
    private ILock lock = new ZookeeperDistrbuteLockV2();

    private OrderNumGenerator orderNumGenerator = new OrderNumGenerator();

    @Override
    public void run() {
        //不加锁
        //getNumber();

        //加锁
        getNumberWithLock();

    }

    public void getNumberWithLock() {
        try {
            lock.getLock();
            String number = orderNumGenerator.getNumber();
            System.out.println("线程：" + Thread.currentThread().getName() + "生成ID" + number);
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unLock();
        }
    }

    public void getNumber() {

        String number = orderNumGenerator.getNumber();
        System.out.println("线程：" + Thread.currentThread().getName() + "生成ID" + number);

    }

    public static void main(String[] args) throws IOException {

        System.out.println("生成唯一订单ID");

        //new ZookeeperDistrbuteLockTest().getNumberWithLock();

        //new ZookeeperDistrbuteLockTest().getNumberWithLock();


//        for (int i = 0; i < 50; i++) {
//            new Thread(new ZookeeperDistrbuteLockTest()).start();
//        }

        //new ZookeeperDistrbuteLockTest().getNumberWithLock();
        //测试V2

        for (int i = 0; i < 10; i++) {
            new Thread(new ZookeeperDistrbuteLockTest()).start();
        }

        //System.in.read();

    }


}
