package com.sl.threadlearning.safe;

public class VoliatileThread2 implements Runnable {
    private int count = 0;
    private boolean isChangeCount = false;

    public boolean isChangeCount() {
        return isChangeCount;
    }

    public void setChangeCount(boolean changeCount) {
        isChangeCount = changeCount;
    }

    @Override
    public void run() {

        //等待isChangeCount修改
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("线程"+Thread.currentThread().getName()+ "进入run了");
        if(isChangeCount){
            count++;
            System.out.println("线程"+Thread.currentThread().getName()+ "执行count++");
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //打印
        System.out.println("线程"+Thread.currentThread().getName()+ "线程被停止了,"+"count值为："+count);
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}