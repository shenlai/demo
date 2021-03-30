package com.sl.threadlearning.safe;

/**
 * 什么是多线程之间同步？
 * 答:当多个线程共享同一个资源,不会受到其他线程的干扰。
 * 线程同步主要是解决线程安全问题
 * <p>
 * 1. synchronized 同步代码块 静态变量作为锁
 * 2. synchronized 同步函数  使用this锁
 * 3. synchronized 静态同步函数  使用的锁是该函数所属的字节码文件 即类名.class锁
 */
public class SynchronizedDemo implements Runnable {

    //private int count = 100;
    //当一个变量被static修饰时，
    private static int count = 100;
    private static Object loclObj = new Object();

    public boolean flag = true;

    /*
    @Override
    public void run() {
        while (count > 0) {
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                // TODO: handle exception
            }
            sale();
        }

    }
    */


    /*
    // 测试synchronized函数使用的是 this锁
    @Override
    public void run() {
        if (flag) {
            while (count > 0) {
                synchronized (this) {
                    if (count > 0) {
                        try {
                            Thread.sleep(50);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - count + 1) + "票");
                        count--;
                    }
                }

            }

        } else {
            while (count > 0) {
                try {
                    syncSale();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
*/


    @Override
    public void run() {
        if (flag) {
            while (count > 0) {
                synchronized (SynchronizedDemo.class) {
                    if (count > 0) {
                        try {
                            Thread.sleep(50);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - count + 1) + "票");
                        count--;
                    }
                }

            }

        } else {
            while (count > 0) {
                try {
                    syncStaticSale();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * synchronized 同步代码块
     */
    public void sale() throws InterruptedException {
        // 前提 多线程进行使用、多个线程只能拿到一把锁。
        // 只有拿到锁的线程才能访问
        synchronized (loclObj) {
            if (count > 0) {
                Thread.sleep(50);
                System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - count + 1) + "票");
                count--;
            }
        }
    }

    /**
     * synchronized 同步函数
     */
    public synchronized void syncSale() throws InterruptedException {
        if (count > 0) {
            Thread.sleep(50);
            System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - count + 1) + "票");
            count--;
        }
    }


    /**
     * synchronized 静态同步函数
     */
    public static synchronized void syncStaticSale() throws InterruptedException {
        if (count > 0) {
            Thread.sleep(50);
            System.out.println(Thread.currentThread().getName() + ",出售第" + (100 - count + 1) + "票");
            count--;
        }
    }

}
