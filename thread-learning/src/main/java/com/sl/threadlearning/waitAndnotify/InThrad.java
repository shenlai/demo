package com.sl.threadlearning.waitAndnotify;


/**
 * 线程同步中使用：
 * wait作用：让当前线程从运行状态变成休眠状态（等待状态），释放锁的资源
 * notify: 让线程从休眠状态变为运行状态
 */
public class InThrad extends Thread {
    private Res res;

    public InThrad(Res res) {
        this.res = res;
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            synchronized (res) {
                //flag=true 不允许生产，只可以消费
                if (res.flag) {
                    try {
                        // 当前线程从运行状态变为休眠状态（等待），但是可以释放锁
                        res.wait();
                    } catch (Exception e) {

                    }
                }
                if (count == 0) {
                    res.userName = "男士";
                    res.userSex = "男";
                } else {
                    res.userName = "女士";
                    res.userSex = "女";
                }
                count = (count + 1) % 2;

                res.flag = true;
                // 唤醒当前线程（唤起消费者）
                res.notify();
            }
        }
    }
}
