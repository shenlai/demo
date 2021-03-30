package com.sl.threadlearning.create;

public class StopThread implements Runnable {

    private volatile boolean flag = true;

    @Override
    public synchronized void run() {
        while (flag) {
            synchronized (this){
                try {
                    this.wait();
                } catch (Exception e) {
                    //e.printStackTrace();
                    stopThread();
                }
            }
        }
        System.out.println("子线程执行结束");
    }

    public void stopThread() {
        synchronized (this){
            System.out.println("调用stopThread方法");
            flag = false;
            System.out.println("已经将flag修改为"+flag);
            this.notify();
        }
    }

}
