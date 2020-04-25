package com.sl.zklock;

import java.io.IOException;

public class ZookeeperDistrbuteLockTest implements Runnable {

    private ILock lock = new ZookeeperDistrbuteLock();

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

        //TODO 有问题，为什么运行不了50次？？
        for (int i = 0; i < 50; i++) {
            new Thread(new ZookeeperDistrbuteLockTest()).start();
        }

        System.in.read();

    }


}
