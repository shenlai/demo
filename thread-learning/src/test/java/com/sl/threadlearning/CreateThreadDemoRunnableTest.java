package com.sl.threadlearning;


import org.junit.Test;

/**
 * 1. 测试 用户线程，守护线程  主线程
 * 2. 测试线程优先级
 *
 */
public class CreateThreadDemoRunnableTest {

    @Test
    public void testThreadExit() throws InterruptedException {
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
    }


    /**
     * 测试线程优先级 Join
     * join作用是让其他线程变为等待, 	t1.join();// 让其他线程变为等待，直到当前t1线程执行完毕，才释放。
     * thread.Join把指定的线程加入到当前线程，可以将两个交替执行的线程合并为顺序执行的线程。比如在线程B中调用了线程A的Join()方法，直到线程A执行完毕后，才会继续执行线程B。
     * @throws InterruptedException
     */
    @Test
    public void testThreadPriority() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 30; i++) {
                    System.out.println("我是子线程...ID" + Thread.currentThread().getId() + "   当前运行index:" + i);
                }
            }
        });

        thread.start();
        //主线程让子线程先执行 子线程thread.join(),提升子线程优先级
        thread.join();
        for (int i = 0; i < 30; i++) {
            System.out.println("主线程...ID" + Thread.currentThread().getId() + "   当前运行index:" + i);
        }
        //
        Thread.sleep(1000);
    }

    /**
     * 三个线程t1 t2 t3 要求执行顺序为 t1 t2 t3
     * @throws InterruptedException
     */
    @Test
    public void testThreadExecuteOrder() throws InterruptedException {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 30; i++) {
                    System.out.println("我是t1子线程...ID" + Thread.currentThread().getId() + "   当前运行index:" + i);
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                //t1.join(); 保证t1在t2执行前执行，执行它时会等待t1执行完成
                for (int i = 0; i < 30; i++) {
                    System.out.println("我是t2子线程...ID" + Thread.currentThread().getId() + "   当前运行index:" + i);
                }
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 30; i++) {
                    System.out.println("我是t3子线程...ID" + Thread.currentThread().getId() + "   当前运行index:" + i);
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
        //这种方式不能保证先执行线程1，再执行线程2，再执行线程3 join方法只能讲线程的优先级提高到高于主线程，但是t1 t2 t3之间并没有优先级
        //实现方式是在线程t2中调用t1.join()【将t1优先级高于t2】,在t3中调用t2.join()
        t1.join();
        t2.join();
        t3.join();

        //主线程让子线程先执行 子线程thread.join(),提升子线程优先级

        System.out.println("主线程...ID" + Thread.currentThread().getId());
        Thread.sleep(1000);
    }

}
