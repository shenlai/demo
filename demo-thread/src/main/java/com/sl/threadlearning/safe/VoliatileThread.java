package com.sl.threadlearning.safe;

public class VoliatileThread implements Runnable {
    private boolean isRunning = true;

    private int count = 0;

    @Override
    public void run() {
        System.out.println("线程"+Thread.currentThread().getName()+ "进入run了");
        while (isRunning) {

        }
        System.out.println("线程"+Thread.currentThread().getName()+ "线程被停止了");
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}