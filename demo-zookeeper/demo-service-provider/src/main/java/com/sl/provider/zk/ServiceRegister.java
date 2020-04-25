package com.sl.provider.zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;


/**
 * 注册到ZK
 */
public class ServiceRegister {

    private static final String BASE_SERVICES = "/services";
    private static final String SERVICE_NAME = "/products";

    public static void register(String address, int port) {
        try {
            ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 5000, (watchedEvent) -> {
            });
            //最终注册到的地址
            String path = BASE_SERVICES + SERVICE_NAME;
            Stat exists = zooKeeper.exists(BASE_SERVICES, false);
            if (exists == null) {
                //创建父节点
                zooKeeper.create(BASE_SERVICES, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            exists = zooKeeper.exists(path, false);
            if (exists == null) {
                //创建子节点
                zooKeeper.create(BASE_SERVICES + SERVICE_NAME, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            //注册服务
            String server_path = address + ":" + port;
            zooKeeper.create(BASE_SERVICES + SERVICE_NAME + "/child", server_path.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.EPHEMERAL_SEQUENTIAL); //临时顺序节点、
            System.out.println("注册成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
