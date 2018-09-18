package com.sl.threadlearning;

import com.sl.threadlearning.create.CreateThreadDemo;
import com.sl.threadlearning.create.CreateThreadDemoRunnable;
import com.sun.media.sound.MidiUtils;
import javafx.scene.Parent;
import org.junit.Test;

public class CreateThreadDemoTest {

    /**
     * 实现Thread类的方式
     */
    @Test
    public void testThreadStart() {
        // 1. 怎么调用线程 通过
        CreateThreadDemo t1 = new CreateThreadDemo();
        // 2.启动线程 不是直接调用run方法，而是调用start方法运行
        // 3.使用开启多线程后，代码不会从上往下进行执行。
        t1.start(); //
        for (int i = 0; i < 30; i++) {
            System.out.println("main,i:" + i);
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

        System.out.println("主线程名称："+Thread.currentThread().getName()+ " 主线程ID:" + Thread.currentThread().getId());
        CreateThreadDemo t1 = new CreateThreadDemo();
        t1.start(); //
        Thread.sleep(1000);
        for (int i = 0; i < 30; i++) {
            System.out.println("main,i:" + i);
        }
    }

}

abstract class TempClass {
    public abstract void add();
}
