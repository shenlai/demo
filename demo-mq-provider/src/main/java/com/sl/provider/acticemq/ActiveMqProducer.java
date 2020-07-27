package com.sl.provider.acticemq;

import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author: sl
 * @Date: 2019/12/16
 * @Description: TODO
 */
@Slf4j
@Component
public class ActiveMqProducer {

    @Autowired
    private JmsTemplate jmsTemplate;




    //test
    public void sendToQueue(String queue, Object message) {
        if (Strings.isNullOrEmpty(queue)) {
            log.error("queue can not empty");
        }
        ActiveMQQueue activeMQQueue = new ActiveMQQueue(queue);
        jmsTemplate.convertAndSend(activeMQQueue, message);
    }


    public void sendToTopic(String topic, Object message) {
        if (Strings.isNullOrEmpty(topic)) {
            log.error("topic can not empty");
        }
        ActiveMQTopic activeMQTopic = new ActiveMQTopic(topic);

        jmsTemplate.convertAndSend(activeMQTopic, message);
    }


}
