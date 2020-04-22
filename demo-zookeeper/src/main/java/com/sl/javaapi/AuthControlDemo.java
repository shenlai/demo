package com.sl.javaapi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


/**
 * ZK权限相关：
 * zookeeper使用ACL机制来实现权限控制, ACL机制主要分为3个方面,权限模式,权限ID和权限
 * <p>
 * 权限模式
 * 　1) IP
 * 　　ip模式是指权限针对这个ip而设置的,比如"ip:192.168.0.6",即允许这个ip访问数据节点
 * 2) digest
 * digest模式是最常用的一种模式,形如"username:password"的方式。
 * 　3) world
 * 该模式对所有用户开放
 * 4) super
 * 超级管理员模式。需要在zkServer.sh中配置，形如"super:password" ，需要重启服务器
 * <p>
 * 权限ID
 * 根据不同模式 授权给的不同对象, ip模式为ip地址, digest模式为username
 * <p>
 * 权限
 * 即允许的操作 CRUD等
 */
public class AuthControlDemo implements Watcher {
    private final static String CONNECTSTRING = "localhost:2181";
    private static CountDownLatch countDownLatch = new CountDownLatch(1);
    private static CountDownLatch countDownLatch2 = new CountDownLatch(1);

    private static ZooKeeper zookeeper;
    private static Stat stat = new Stat();

    public static void main(String[] args) throws Exception {
        zookeeper = new ZooKeeper(CONNECTSTRING, 500000, new AuthControlDemo());
        //不用先执行countDownLatch.countDown() ？？？
        countDownLatch.await();

        ACL acl = new ACL(ZooDefs.Perms.ALL, new Id("digest", DigestAuthenticationProvider.generateDigest("root:root")));
        ACL acl2 = new ACL(ZooDefs.Perms.CREATE, new Id("ip", "192.168.1.1"));

        List<ACL> acls = new ArrayList<>();
        acls.add(acl);
        acls.add(acl2);
        zookeeper.create("/auth1", "123".getBytes(), acls, CreateMode.PERSISTENT);
        zookeeper.addAuthInfo("digest", "root:root".getBytes());
        zookeeper.create("/auth1/auth1-1", "123".getBytes(), ZooDefs.Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);


        ZooKeeper zooKeeper1 = new ZooKeeper(CONNECTSTRING, 5000, new AuthControlDemo());
        countDownLatch.await();
        //必须得先授权再删除
        zooKeeper1.addAuthInfo("digest", "root:root".getBytes());
        zooKeeper1.delete("/auth1", -1);
        zooKeeper1.delete("/auth1/auth1-1", -1);


        // acl (create /delete /admin /read/write)
        //权限模式： ip/Digest（username:password）/world/super

    }

    public void process(WatchedEvent watchedEvent) {
        //如果当前的连接状态是连接成功的，那么通过计数器去控制
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            if (Event.EventType.None == watchedEvent.getType() && null == watchedEvent.getPath()) {
                countDownLatch.countDown();
                System.out.println(watchedEvent.getState() + "-->" + watchedEvent.getType());
            }
        }

    }
}
