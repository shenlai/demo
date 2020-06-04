package com.sl.threadlearning.waitAndnotify;

public class OutThread extends Thread {
    private Res res;

    public OutThread(Res res) {
        this.res = res;
    }

    @Override
    public void run() {
        while (true) {
//            synchronized (res) {
//                if (!res.flag) { //flag=false: 不可消费，必须等待
//                    try {
//                        res.wait();
//                    } catch (Exception e) {
//                        // TODO: handle exception
//                    }
//                }
//                //执行消费逻辑
//                System.out.println(res.userName + "--" + res.userSex);
//                //消费完，将标志改为false,允许生产，并唤起生产者
//                res.flag = false;
//                res.notify();
//            }


            synchronized (res) {
                if (res.flag) { //flag=true: 可消费
                    //执行消费逻辑
                    System.out.println(res.userName + "--" + res.userSex);
                    //消费完，将标志改为false,允许生产，并唤起生产者
                    res.flag = false;
                }

            }
        }
    }
}

