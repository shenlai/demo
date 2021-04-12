package com.sl.threadlearning.queuelearn;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 阻塞队列
 */
public class ProducerThread implements Runnable {

    private BlockingQueue queue;
    private volatile boolean flag = true;

    private static AtomicInteger count = new AtomicInteger();

    public ProducerThread(BlockingQueue queue) {
        this.queue = queue;
    }


    @Override
    public void run() {

        try {
            System.out.println("生产线程启动。。。");
            while (flag) {
                System.out.println("正在生产数据....");
                String data = count.incrementAndGet() + " ";
                //将数据存入队列
                boolean offer = queue.offer(data, 2, TimeUnit.SECONDS);
                if (offer) {
                    System.out.println("生产者,存入" + data + "到队列中,成功.");
                } else {
                    System.out.println("生产者,存入" + data + "到队列中,失败.");
                }
                Thread.sleep(1000);

            }
        } catch (Exception e) {

        } finally {
            System.out.println("生产者退出线程");
        }


    }

    public void stop() {
        this.flag = false;
    }


}
