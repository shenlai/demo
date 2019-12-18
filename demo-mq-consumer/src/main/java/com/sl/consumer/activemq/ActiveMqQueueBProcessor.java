package com.sl.consumer.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Author: sl
 * @Date: 2019/12/12
 * @Description: TODO
 */
@Component
public class ActiveMqQueueBProcessor {
    /*
     * 监听和读取消息
     */
    @JmsListener(destination = "active.queue.testB", containerFactory = "jmsListenerContainerQueue")
    //@JmsListener(destination = "active.queue.testB")
    public void readActiveQueue(String message) {
        System.out.println("ActiveMqQueueBProcessor active.queue.testB  receive：" + message);
    }

}
