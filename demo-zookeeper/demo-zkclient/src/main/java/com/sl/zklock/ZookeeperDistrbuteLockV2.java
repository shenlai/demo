package com.sl.zklock;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 基于ZKClient 实现分布式锁
 * 排队获取锁
 */
public class ZookeeperDistrbuteLockV2 extends ZookeeperAbstractLock {


    private CountDownLatch countDownLatch = null;

    private String beforPath;    //当前请求的节点 前一个节点
    private String currentPath;  //当前请求的节点


    public ZookeeperDistrbuteLockV2() {
        //创建父节点
        if (!this.getInstance.exists(path2)) {
            //创建持久化节点
            this.getInstance.createPersistent(path2);
        }

    }

    @Override
    public boolean tryLock() {
        //如果currentPath为空则为第一次尝试加锁，第一次加锁赋值currentPath
        if (currentPath == null || currentPath.length() <= 0) {
            //创建一个临时顺序节点
            currentPath = this.getInstance.createEphemeralSequential(path2 + "/", "lock");
            System.out.println
                    (Thread.currentThread().getName() +
                            "->创建节点:" + currentPath);

        }
        //获取多有临时节点并排序，临时节点名称为自增长的字符串，如：00000000700
        List<String> childrens = this
                .getInstance.getChildren(path2);

        Collections.sort(childrens);

        if (currentPath.equals(path2 + "/" + childrens.get(0))) {
            return true;
        } else {
            //如果当前节点在所有节点中排名顺序不是第一个，则获前面排名的节点名称，并赋值给beforePath
            int wz = Collections.binarySearch(childrens, currentPath.substring(7)); //当前节点序号
            beforPath = path2 + "/" + childrens.get(wz - 1);
        }
        return false;
    }

    /**
     *
     */
    @Override
    public void waitLock() {
        //如果节点存在
        if (getInstance.exists(beforPath)) {
            System.out.println(Thread.currentThread().getName()
                    + "等待的节点是:" + beforPath);
            countDownLatch = new CountDownLatch(1);

            IZkDataListener iZkDataListener = new IZkDataListener() {
                //监听节点数据变化变数
                @Override
                public void handleDataChange(String s, Object o) throws Exception {
                    //countDownLatch.countDown(); //count -1
                }

                @Override
                public void handleDataDeleted(String s) throws Exception {
                    countDownLatch.countDown(); //count -1
                }
            };

            //注册事件监听,与V1的差异是，监听前面一个节点的变化
            getInstance.subscribeDataChanges(beforPath, iZkDataListener);


            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //删除监听
            //getInstance.unsubscribeDataChanges(beforPath, iZkDataListener);
        }


    }


    @Override
    public void unLock() {
        getInstance.delete(currentPath);                   //监听节点变化 handleDataDeleted
        //getInstance.writeData(currentPath, "changed");  //监听节点变化 handleDataChange
        getInstance.close();

    }


}
