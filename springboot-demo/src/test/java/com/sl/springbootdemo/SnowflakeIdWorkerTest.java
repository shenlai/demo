package com.sl.springbootdemo;

import com.sl.springbootdemo.IdGenerate.SnowflakeIdWorker;
import org.junit.Test;

/**
 * @Author: sl
 * @Date: 2018/12/26
 * @Description: TODO
 */
public class SnowflakeIdWorkerTest {

    @Test
    public void TestIdGen() {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        for (int i = 0; i < 1000; i++) {
            long id = idWorker.nextId();
            System.out.println(Long.toBinaryString(id));
            System.out.println(id);
        }
    }

    @Test
    public void TestBaiduIdGen() {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        for (int i = 0; i < 1000; i++) {
            long id = idWorker.nextId();
            System.out.println(Long.toBinaryString(id));
            System.out.println(id);
        }
    }



}
