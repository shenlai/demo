package com.sl.threadlearning.lockLearn;

import com.sl.threadlearning.waitAndnotify.Res;

import java.util.concurrent.locks.Condition;


public class OutThreadT extends Thread {
    private ResT res;
    private Condition newCondition;

    public OutThreadT(ResT res,Condition newCondition) {
        this.res = res;
        this.newCondition =newCondition;
    }

    @Override
    public void run() {
        while (true) {
            res.lock.lock();
            if (!res.flag) { //flag=false: 不可消费，必须等待
                try {
                    // res.wait();
                    this.newCondition.await();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            //执行消费逻辑
            System.out.println(res.userName + "--" + res.userSex);
            //消费完，将标志改为false,允许生产，并唤起生产者
            res.flag = false;
            this.newCondition.signal();

        }
    }
}

