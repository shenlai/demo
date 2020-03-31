package com.sl.provider;

import com.alibaba.fastjson.JSON;
import com.sl.provider.acticemq.ActiveMqProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.Message;

/**
 * @Author: sl
 * @Date: 2019/12/12
 * @Description: TODO
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActiveMqProviderTest {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private JmsTemplate jmsTemplate;

    //重试
//    @Autowired
//    private JmsTemplate jmsTemplateSl;

    @Autowired
    private ActiveMqProducer activeMqProducer;


    /**
     * 重试测试
     */
    @Test
    public void sendActiveMqQueueRetry() {

        String msg = "hello msg retryQueue";
        jmsTemplate.convertAndSend("active.queue.retryTestA", msg);
        //jmsTemplateSl.convertAndSend("active.queue.retryTestA", msg);
    }


    @Test
    public void sendActiveMqQueueReceive() {
        Message message = jmsTemplate.receive("active.queue.test");
        log.info(JSON.toJSONString(message));
    }

    @Test
    public void sendActiveMqQueue1() {

        String msg = "hello msg sdasd";
        jmsTemplate.convertAndSend("active.queue.test", msg);
    }


    @Test
    public void sendActiveMqQueue() {
        for (int i = 0; i < 1; i++) {
            String msg = "hello msg sdasd";
            activeMqProducer.sendToQueue("active.queue.testA", msg);

        }

    }


    @Test
    public void sendActiveMqQueueB() {
        for (int i = 0; i < 20; i++) {
            activeMqProducer.sendToQueue("active.queue.testB", "hello textB");
        }
    }


    @Test
    public void sendActiveMqTopic() {
        String msg = "hello topic";
        for (int i = 0; i < 20; i++) {
            activeMqProducer.sendToTopic("active.topicA.test", msg);
        }

    }


}
