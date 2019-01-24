package com.sl.springbootdemo;

import com.sl.springbootdemo.IdGenerate.MyDefineUidUtil;
import org.junit.AfterClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @Author: sl
 * @Date: 2018/12/27
 * @Description: TODO
 */
public class MyDefineUidUtilTest {

    private static final Logger LOG = LoggerFactory.getLogger(MyDefineUidUtilTest.class);

    private static long speed = 0;

    @AfterClass
    public static void printSpeed() {
        System.out.println(String.format("Speed: %d/s", speed));
    }

    @Test
    public void TestIdGen() throws InterruptedException {
        List<String> guidList =   Collections.synchronizedList(new ArrayList<String>());
        Set<String> uidSet = new ConcurrentSkipListSet<>();

        List<Thread> threadList = new ArrayList<Thread>();
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {// 开启10个线程
            Thread thread =   new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {// 每个线程循环1w条数据
                        try {
                            String guid = MyDefineUidUtil.nextId();
                            guidList.add(guid);
                            boolean res = uidSet.add(guid);
                            if(!res)
                            {
                                LOG.info("guid重复", guid);
                            }

                        } catch (UnknownHostException e) {
                            LOG.error("未知主机IP错误-->【{}】", e);
                        }
                    }
                }
            });
            threadList.add(thread);
            thread.start();
        }

        // Wait for worker done
        for (Thread thread : threadList) {
            thread.join();
        }

        long end = System.currentTimeMillis();
        speed = guidList.size() / (end - start) * 1000;

        LOG.info("guidList size:{}", guidList.size());
        LOG.info("set size:{}", uidSet.size());
        LOG.info("没有重复元素-->【{}】", MyDefineUidUtil.hasSame(guidList));
    }

    @Test
    public void TestIdGen2() throws UnknownHostException {
        String uid = MyDefineUidUtil.nextId();
        //System.out.println(uid);

        String sequenceStr = String.format("%05d", 12);

        String sequenceStrx = String.format("%05d", 199922);
        System.out.println(sequenceStr);
        System.out.println(sequenceStrx);
    }
}
