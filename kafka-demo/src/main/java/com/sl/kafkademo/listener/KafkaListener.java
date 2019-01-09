package com.sl.kafkademo.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Auther: sl
 * @Date: 2018/9/14 08:50
 * @Description:
 */
@Component
public class KafkaListener {

    private static final Logger logger = LoggerFactory.getLogger(KafkaListener.class);

    @org.springframework.kafka.annotation.KafkaListener(topics = "common")
    public void receive(ConsumerRecord<String, String> consumer) {
        logger.info("{} - {} : {}", consumer.topic(), consumer.key(), consumer.value());
    }
}
