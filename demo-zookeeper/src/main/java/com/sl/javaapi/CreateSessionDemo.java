package com.sl.javaapi;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


/**
 * 获得连接
 */
public class CreateSessionDemo {
    private final static String CONNECTSTRING = "localhost:2181";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws IOException, InterruptedException {
        //watcher 确认连接成功
        ZooKeeper zooKeeperTemp = new ZooKeeper(CONNECTSTRING, 5000, null);

        ZooKeeper zooKeeper = new ZooKeeper(CONNECTSTRING, 5000,
                new Watcher() {
                    public void process(WatchedEvent watchedEvent) {
                        //如果当前的连接状态是连接成功的，那么通过计数器去控制
                        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                            countDownLatch.countDown();
                            System.out.println(watchedEvent.getState());
                        }
                    }
                });
        countDownLatch.await();
        //查看ZK连接状态，确认已连接上
        System.out.println(zooKeeper.getState());   //  CONNECTED 已连接
    }
}
