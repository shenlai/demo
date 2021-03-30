package com.sl.threadlearning;

import org.junit.Test;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    @Test
    public  void testCyclicBarrier() throws InterruptedException {
        CyclicBarrier cyclicBarrier=new CyclicBarrier(5);
        for (int i = 0; i < 5; i++) {
            WriterUtil writer = new WriterUtil(cyclicBarrier);
            writer.start();
        }

        Thread.sleep(4000);
    }
}
