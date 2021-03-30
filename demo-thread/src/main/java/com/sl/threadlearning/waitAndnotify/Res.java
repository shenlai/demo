package com.sl.threadlearning.waitAndnotify;

public class Res {
    public String userSex;
    public String userName;

    //线程通讯标识，true:消费者可以消费，生产者线程等待，
    //              false:生产者线程可以生产， 消费者等待
    // 默认为false：先执行生产者线程
    public boolean flag = false;

}
