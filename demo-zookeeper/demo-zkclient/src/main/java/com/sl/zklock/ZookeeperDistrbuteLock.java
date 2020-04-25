package com.sl.zklock;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

/**
 * 基于ZKClient 实现分布式锁
 */
public class ZookeeperDistrbuteLock extends ZookeeperAbstractLock {


    private CountDownLatch countDownLatch = null;


    @Override
    public boolean tryLock() {
        try {
            //创建临时节点
            getInstance().createEphemeral(path);
            return true;
        } catch (Exception e) {
            //创建失败
            //e.printStackTrace();
            return false;
        }
    }

    /**
     * //countDownLatch调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
     * countDownLatch这个类使一个线程等待其他线程各自执行完毕后再执行。
     * countDown()将count值减1是通过一个计数器来实现的，计数器的初始值是线程的数量。
     * 每当一个线程执行完毕后，计数器的值就-1，当计数器的值为0时，表示所有线程都执行完毕，然后在闭锁上等待的线程就可以恢复工作了。
     */
    @Override
    public void waitLock() {
        IZkDataListener iZkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                //监听到变化，count-1 ,唤醒被等待的线程
                if (countDownLatch != null) {
                    countDownLatch.countDown(); //count -1
                }
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {

            }
        };

        //注册事件监听
        getInstance().subscribeDataChanges(path, iZkDataListener);


        try {
            //等待直到 接收到事件通知
            //如果节点存在
            if (getInstance().exists(path)) {
                countDownLatch = new CountDownLatch(1);
                countDownLatch.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //删除监听
        getInstance().unsubscribeDataChanges(path, iZkDataListener);
    }


    @Override
    public void unLock() {
        if (getInstance() != null) {
            getInstance().delete(path);
            //getInstance().close();
            System.out.println("释放锁资源");
        }

    }


}
