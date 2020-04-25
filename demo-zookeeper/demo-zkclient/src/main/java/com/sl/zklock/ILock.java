package com.sl.zklock;

public interface ILock {

    //获取锁资源
    void getLock();

    //释放锁
    void unLock();
}
