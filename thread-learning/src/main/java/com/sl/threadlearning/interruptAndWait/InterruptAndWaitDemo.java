package com.sl.threadlearning.interruptAndWait;

import com.sl.threadlearning.waitAndnotify.Res;

/**
 * @Author: sl
 * @Date: 2020/4/11
 * @Description: TODO
 *
 * https://www.cnblogs.com/xrq730/p/4853932.html
 * interrupt()方法的作用不是中断线程，而是在线程阻塞的时候给线程一个中断标识，表示该线程中断。wait()就是"阻塞的一种场景"，看一下用interrupt()打断wait()的例子
 */
public class InterruptAndWaitDemo extends Thread {

    private Res res;

    public InterruptAndWaitDemo(Res res) {
        this.res = res;
    }

    @Override
    public void run() {
        try{

            synchronized (res) {
                System.out.println("begin wait");
                res.wait();
                System.out.println("end wait");
            }

        }catch (Exception e){
            System.out.println("wait()被interrupt()打断了！");
            e.printStackTrace();
        }


    }
}
