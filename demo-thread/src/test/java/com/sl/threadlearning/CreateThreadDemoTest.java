package com.sl.threadlearning;

import com.sl.threadlearning.create.CreateThreadDemo;
import com.sl.threadlearning.create.CreateThreadDemoRunnable;
import com.sun.media.sound.MidiUtils;
import javafx.scene.Parent;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CreateThreadDemoTest {

    /**
     * 实现Thread类的方式
     */
    @Test
    public void testThreadStart() throws InterruptedException {
        // 1. 怎么调用线程 通过
        CreateThreadDemo t1 = new CreateThreadDemo();
        // 2.启动线程 不是直接调用run方法，而是调用start方法运行
        // 3.使用开启多线程后，代码不会从上往下进行执行。
        t1.setDaemon(true); //设置为守护线程
        t1.start(); //   调用start()方法表示此线程可以运行了（不一定立即运行），正在等待CPU调用线程对象得run()方法
        for (int i = 0; i < 30; i++) {
            System.out.println("main,i:" + i);
        }
    }

    /**
     * 测试守护线程
     */
    @Test
    public void testThreadStart2() throws InterruptedException {
        CreateThreadDemo t1 = new CreateThreadDemo();
        //t1.setDaemon(true); //设置为守护线程
        t1.start(); //   调用start()方法表示此线程可以运行了（不一定立即运行），正在等待CPU调用线程对象得run()方法
        TimeUnit.SECONDS.sleep(10);
        System.out.println("用户线程要退出了，后面t1不再打印了");  //为啥测试方法总是停止打印？
    }

    // 区别，测试方法结算，应用停止？？
//    public static void main(String[] args) {
//        try {
//            CreateThreadDemo t1 = new CreateThreadDemo();
//            //t1.setDaemon(true); //设置为守护线程
//            t1.start(); //   调用start()方法表示此线程可以运行了（不一定立即运行），正在等待CPU调用线程对象得run()方法
//            TimeUnit.SECONDS.sleep(10);
//            System.out.println("用户线程要退出了，后面t1不再打印了");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }


    //测试interrupt()
    public static void main(String[] args) {
        try {
            CreateThreadDemo mt = new CreateThreadDemo();
            mt.start();
            Thread.sleep(2000);
            mt.join();
            mt.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 实现Runnable接口方式，启动线程
     */
    @Test
    public void testThreadStartRuunable() {

        CreateThreadDemoRunnable t = new CreateThreadDemoRunnable();
        //借助Thread类 启动线程
        Thread thread = new Thread(t);
        thread.start();
        for (int i = 0; i < 30; i++) {
            System.out.println("主线程 i:" + i);
        }
    }


    /**
     * 使用匿名类的方式创建线程
     */
    @Test
    public void testThteadStartInternalClass() {
        /*
        //什么是匿名 内部类
        TempClass tempClass = new TempClass(){
            @Override
            public  void add(){
                System.out.println("使用自定义内部类");
            }
        };
        tempClass.add();
        */

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //子线程指执行
                for (int i = 0; i < 30; i++) {
                    System.out.println("子线程，i:" + i);
                }
            }
        });

        thread.start();
        for (int i = 0; i < 30; i++) {
            System.out.println("主线程，i:" + i);
        }
    }


    /**
     * 获取子线程ID  主线程ID
     */
    @Test
    public void testThreadApi() throws InterruptedException {

        System.out.println("主线程名称：" + Thread.currentThread().getName() + " 主线程ID:" + Thread.currentThread().getId());
        CreateThreadDemo t1 = new CreateThreadDemo();
        t1.start(); //
        Thread.sleep(1000);
        for (int i = 0; i < 30; i++) {
            System.out.println("main,i:" + i);
        }
    }

}


