package com.sl.threadlearning.create;


import java.util.concurrent.TimeUnit;

/**
 * 创建线程有哪些方式：
 * 1. 使用继承Thread类方式，继承Thread，重写run
 * 2. 实现runable接口方式（继承Thread类也是实现runable接口）  -> 还是要通过Thread类创建线程
 * 3. 使用匿名内部类方式
 * 4. callable !!!!!
 * 5. 使用线程池创建线程
 *
 *
 * <p>
 * 虚拟机中的线程状态有六种，定义在Thread.State中：
 * 1、新建状态NEW        new了但是没有启动的线程的状态。比如"Thread t = new Thread()"，t就是一个处于NEW状态的线程
 * <p>
 * 2、可运行状态RUNNABLE new出来线程，调用start()方法即处于RUNNABLE状态了。处于RUNNABLE状态的线程可能正在Java虚拟机中运行，
 * 也可能正在等待处理器的资源，因为一个线程必须获得CPU的资源后，才可以运行其run()方法中的内容，否则排队等待
 * <p>
 * 3、阻塞BLOCKED       如果某一线程正在等待监视器锁，以便进入一个同步的块/方法，那么这个线程的状态就是阻塞BLOCKED
 * <p>
 * 4、等待WAITING       某一线程因为调用不带超时的Object的wait()方法、不带超时的Thread的join()方法、LockSupport的park()方法，就会处于等待WAITING状态
 * <p>
 * 5、超时等待TIMED_WAITING  某一线程因为调用带有指定正等待时间的Object的wait()方法、Thread的join()方法、Thread的sleep()方法、LockSupport的parkNanos()方法、LockSupport的parkUntil()方法，就会处于超时等待TIMED_WAITING状态
 * <p>
 * 6、终止状态TERMINATED  线程调用终止或者run()方法执行结束后，线程即处于终止状态。处于终止状态的线程不具备继续运行的能力
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * Thread中的方法：
 * 1: start()   调用start()方法表示此线程可以运行了（不一定立即运行），正在等待CPU调用线程对象得run()方法
 * 2: run()     线程开始执行，虚拟机调用的是线程run()方法中的内容, 直接new MyThread().run() 同步调用
 * 3: isAlive()  native 测试线程是否处于活动状态，只要线程启动且没有终止，方法返回的就是true
 * 7: isDaeMon、setDaemon(boolean on)  讲解两个方法前，首先要知道理解一个概念。Java中有两种线程，一种是用户线程，一种是守护线程。守护线程是一种特殊的线程，
 * 它的作用是为其他线程的运行提供便利的服务，最典型的应用便是GC线程。如果进程中不存在非守护线程了，那么守护线程自动销毁，因为没有存在的必要，为别人服务，
 * 结果服务的对象都没了，当然就销毁了
 * 8: interrupt() interrupt()方法的作用实际上是：在线程受到阻塞时抛出一个中断信号，这样线程就得以退出阻塞状态。换句话说，没有被阻塞的线程，调用interrupt()方法是不起作用的
 * 9: join方法
 *
 *
 *
 *
 *
 *
 *
 */
public class CreateThreadDemo extends Thread {
    /**
     * run方法就是线程需要执行的任务或者执行的代码
     */
    @Override
    public void run() {
        for (int i = 0; i < 30; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("run,i:" + i + "  线程ID:" + getId());
        }

    }
}
