package com.sl.consumer.activemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @Author: sl
 * @Date: 2019/12/12
 * @Description: TODO
 */
@Slf4j
@Component
public class ActiveMqTopicAProcessorOne {
    /*
     * 监听和读取消息
     */
    @JmsListener(destination = "active.topicA.test",containerFactory = "jmsListenerContainerTopic")
    public void readActiveQueue(String message) {
        System.out.println("ActiveMqTopicProcessorA active.topicA.test  receive：" + message);
    }

}
