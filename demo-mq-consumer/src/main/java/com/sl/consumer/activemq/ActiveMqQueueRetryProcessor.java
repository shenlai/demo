package com.sl.consumer.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.apache.activemq.Message;
import org.apache.activemq.command.ActiveMQObjectMessage;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @Author: sl
 * @Date: 2019/12/12
 * @Description: 测试重试队列
 */
@Component
public class ActiveMqQueueRetryProcessor {
    /*
     * 监听和读取消息
     */
    @JmsListener(destination = "active.queue.retryTestA", containerFactory = "jmsListenerContainerQueueSl")
    //public void readActiveQueue(String message, Session session) throws JMSException {
    //public void readActiveQueue(Message message, Session session) throws JMSException {
    public void readActiveQueue(final TextMessage text, Session session) throws JMSException {
        try {
            System.out.println("ActiveMqQueueProcessor active.queue.retryTestA  receive：" + text.getText());
            int i = 1 / 0;//抛异常


            // success
            text.acknowledge();// 使用手动签收模式，需要手动的调用，如果不在catch中调用session.recover()消息只会在重启服务后重发
        } catch (Exception e) {
            session.recover(); // 此不可省略 重发信息使用（执行失败，需要重试）
        }


    }

}
