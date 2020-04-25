package com.sl.consumer.listener;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import com.sl.consumer.utils.LoadBalance;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.ArrayList;
import java.util.List;


public class InitListener implements ServletContextListener {

    private static final String BASE_SERVICES = "/services";
    private static final String SERVICE_NAME = "/products";

    private ZooKeeper zooKeeper;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            //连接ZK，获取列表信息
            zooKeeper = new ZooKeeper("localhost:2181", 50000, (watchedEvent) -> {
                if (watchedEvent.getType() == Watcher.Event.EventType.NodeChildrenChanged && watchedEvent.getPath().equals(BASE_SERVICES + SERVICE_NAME)) {
                    //监听节点变更，刷新节点列表
                    updateServiceList();
                }
            });
            //第一次连接的时候获取节点列表
            updateServiceList();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void updateServiceList() {
        try {
            List<String> children = zooKeeper.getChildren(BASE_SERVICES + SERVICE_NAME, true);
            List<String> newServerList = new ArrayList<String>();
            for (String subNode : children) {
                byte[] data = zooKeeper.getData(BASE_SERVICES + SERVICE_NAME + "/" + subNode, false, null);
                String host = new String(data, "utf-8");
                System.out.println("host:" + host);
                newServerList.add(host);
            }
            LoadBalance.SERVICE_LIST = newServerList;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
