package com.sl.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootRabbitmqApplicationTests {


    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    public void contextLoads() {
        //rabbitTemplate.send(exchange,message)

        //object默认当成消息体，传入要发送的对象，自动序列化发送给rabbitmq
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "消息");
        map.put("data", Arrays.asList("helloword", 123, true));

        rabbitTemplate.convertAndSend("exchange.direct", "testkey", map);
    }


    @Test
    public void receive() {
       Object obj = rabbitTemplate.receiveAndConvert("test");
       System.out.println(obj.getClass());
       System.out.println(obj);
    }

}
