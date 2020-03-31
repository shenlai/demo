package com.sl.consumer.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Author: sl
 * @Date: 2019/12/12
 * @Description: TODO
 */

@Component
public class ActiveMqTopicAProcessorTwo {
    /*
     * 监听和读取消息
     */
    @JmsListener(destination = "active.topicA.test",containerFactory = "jmsListenerContainerTopic")
    public void readActiveQueue(String message) {
        System.out.println("ActiveMqTopicProcessorB active.topicA.test  receive：" + message);
    }

}
