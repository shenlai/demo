package com.sl.threadlearning.queuelearn;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * BlockingQueue :阻塞线程安全队列
 *
 *  BlockingQueue即阻塞队列，从阻塞这个词可以看出，在某些情况下对阻塞队列的访问可能会造成阻塞。被阻塞的情况主要有如下两种：
 * 1. 当队列满了的时候进行入队列操作
 * 2. 当队列空了的时候进行出队列操作
 * 因此，当一个线程试图对一个已经满了的队列进行入队列操作时，它将会被阻塞，除非有另一个线程做了出队列操作；
 * 同样，当一个线程试图对一个空队列进行出队列操作时，它将会被阻塞，除非有另一个线程进行了入队列操作。
 *
 * ConcurrentLinkedDeque:非阻塞线程安全队列
 *
 *
 *
 *
 *
 */
public class BlockingQueueTest {

    @Test
    public  void testBlocking() throws InterruptedException {

        //有界队列，长度10,
        BlockingQueue<String> queue = new LinkedBlockingQueue<String>(10);

        ProducerThread producerThread1 = new ProducerThread(queue);
        ProducerThread producerThread2 = new ProducerThread(queue);

        ConsumerThread consumerThread1 = new ConsumerThread(queue);
        Thread t1 = new Thread(producerThread1);
        Thread t2 = new Thread(producerThread2);

        Thread c1 = new Thread(consumerThread1);
        t1.start();
        t2.start();
        c1.start();

        // 执行10s
        Thread.sleep(10 * 1000);
        producerThread1.stop();
        producerThread2.stop();

        Thread.sleep(2000);

    }
}
