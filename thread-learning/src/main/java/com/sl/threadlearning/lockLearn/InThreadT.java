package com.sl.threadlearning.lockLearn;


import com.sl.threadlearning.waitAndnotify.Res;

import javax.security.auth.login.Configuration;
import java.util.concurrent.locks.Condition;

/**
 *  线程同步中使用：
 *  wait作用：让当前线程从运行状态变成休眠状态（等待状态），释放锁的资源
 *  notify: 让线程从休眠状态变为运行状态
 */
public class InThreadT extends Thread {
    private ResT res;
    private Condition newCondition;

    public InThreadT(ResT res,Condition newCondition) {
        this.res = res;
        this.newCondition =newCondition;
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            try{
                //上锁
                res.lock.lock();
                    //flag=true 不允许生产，只可以消费
                    if (res.flag) {
                        try {
                            // 当前线程从运行状态变为休眠状态（等待），但是可以释放锁
                            //res.wait();
                            this.newCondition.await();
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
                    //notify
                   this.newCondition.signal();
            }catch (Exception e){

            }finally {
                res.lock.unlock();
            }


        }
    }
}
