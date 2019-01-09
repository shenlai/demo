package com.sl.kafkademo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: sl
 * @Date: 2018/9/14 08:47
 * @Description:
 */
@RestController
@RequestMapping(value = "kafka")
public class KafkaController {

    private static final Logger logger = LoggerFactory.getLogger(KafkaController.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    //@Value("${app.topic.common}")
    //private String topic;

    private static final  String topic = "common";

    @RequestMapping(value = "send", method = RequestMethod.GET)
    @ResponseBody
    public void send(String key, String data) {
        kafkaTemplate.send(topic, key, data);
    }
}
