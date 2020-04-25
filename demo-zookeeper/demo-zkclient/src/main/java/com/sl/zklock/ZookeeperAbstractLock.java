package com.sl.zklock;

import org.I0Itec.zkclient.ZkClient;

public abstract class ZookeeperAbstractLock extends AbstractLock {

    private static final String ConnectString = "localhost:2181";

    //获取ZK
//    protected static ZkClient getInstance() {
//
//        //return new ZkClient(ConnectString, 10000);
//        return new ZkClient(ConnectString);
//    }
    private static ZkClient zkClient = null;
    private static Object lock = new Object();

    protected ZkClient getInstance() {
        if (zkClient == null) {
            synchronized (lock) {
                if (zkClient == null) {
                    zkClient = new ZkClient(ConnectString);
                }
            }
        }
        return zkClient;
    }

    protected static final String path = "/lock";

    protected static final String path2 = "/lock2";


}
