package com.sl.provider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: sl
 * @Date: 2019/12/12
 * @Description: TODO
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActiveMqProviderTest {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private JmsTemplate jmsTemplate;


    @Test
    public void sendActiveMq() {
        String msg = "hello msg 23424321";
        jmsTemplate.convertAndSend("active.queue.test",msg);
    }

}
