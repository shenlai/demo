package com.sl.zklock;

import java.util.concurrent.locks.Lock;

public abstract class AbstractLock implements ILock {


    @Override
    public void getLock() {

        if (tryLock()) {
            System.out.println("####获取lock资源####");
        } else {
            //等待
            waitLock();
            // 重新获取去锁资源
            getLock();
        }

    }

    //获取锁资源
    public abstract boolean tryLock();

    //等待
    public abstract void waitLock();


}
