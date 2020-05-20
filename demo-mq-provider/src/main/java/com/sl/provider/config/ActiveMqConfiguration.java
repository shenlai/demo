package com.sl.provider.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;

/**
 * @Author: sl
 * @Date: 2019/12/16
 * @Description: TODO
 */
@Configuration
public class ActiveMqConfiguration {

    @Value("${spring.activemq.broker-url}")
    private String brokerURL;

    /**
     * 默认jmsTemplate
     *
     * @param connectionFactory
     * @return
     */
    @Bean(name = "jmsTemplate")
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        return jmsTemplate;
    }

    //region /**************默认方式****************************************************/
    @Bean(name = "connectionFactory")
    public ActiveMQConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory(brokerURL);
    }

    /**
     * 处理queue消息
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }


    /**
     * 处理topic消息
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ActiveMQConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setPubSubDomain(true); //开启 发布订阅模式
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }
    //endregion

    //region/**************失败重试****************************************************/

    @Bean(name = "connectionFactorySl")
    public ActiveMQConnectionFactory connectionFactorySl(RedeliveryPolicy redeliveryPolicy) {
        ActiveMQConnectionFactory factorySl = new ActiveMQConnectionFactory(brokerURL);
        factorySl.setRedeliveryPolicy(redeliveryPolicy);
        factorySl.setTrustAllPackages(true);
        return factorySl;
        //return new ActiveMQConnectionFactory("admin", "admin", "tcp://localhost:61616");
    }

    /**
     * 消息重试配置项
     *
     * @return
     */
    @Bean
    public RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        //是否在每次尝试重新发送失败后,增长等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //设置重发最大拖延时间, -1表示没有拖延，只有setUseExponentialBackOff(true)时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
        //重发次数，默认3次
        redeliveryPolicy.setMaximumRedeliveries(3);
        //重发时间间隔
        redeliveryPolicy.setInitialRedeliveryDelay(1);
        //第一次失败后重发等待500毫秒，第二次500*2， 依次类推
        redeliveryPolicy.setBackOffMultiplier(2);
        //是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        return redeliveryPolicy;
    }


    /**
     * ACK模式描述了Consumer与broker确认消息的方式(时机),比如当消息被Consumer接收之后,Consumer将在何时确认消息。
     * 对于broker而言，只有接收到ACK指令,才会认为消息被正确的接收或者处理成功了,通过ACK，可以在consumer（/producer）
     * 与Broker之间建立一种简单的“担保”机制.
     * 手动确认和单条消息确认需要手动的在客户端调用message.acknowledge()
     *
     * @param connectionFactorySl
     * @return
     */
//    @Bean(name = "jmsTemplateSl")
//    public JmsTemplate jmsTemplateSl(@Qualifier("connectionFactorySl") ActiveMQConnectionFactory connectionFactorySl) {
//        JmsTemplate jmsTemplate = new JmsTemplate();
//        //设置持久化 1非持久化，2持久化
//        jmsTemplate.setDeliveryMode(2);
//
//        // SESSION_TRANSACTED = 0  事物提交并确认
//        // AUTO_ACKNOWLEDGE = 1    自动确认
//        // CLIENT_ACKNOWLEDGE = 2    客户端手动确认   
//        // DUPS_OK_ACKNOWLEDGE = 3    自动批量确认
//        // INDIVIDUAL_ACKNOWLEDGE = 4  客户端签收模式
//        jmsTemplate.setSessionAcknowledgeMode(4);
//        jmsTemplate.setConnectionFactory(connectionFactorySl);
//        return jmsTemplate;
//    }
    @Bean("jmsListenerContainerQueueSl")
    public DefaultJmsListenerContainerFactory listener(@Qualifier("connectionFactorySl") ActiveMQConnectionFactory connectionFactorySl) {
        DefaultJmsListenerContainerFactory listener = new DefaultJmsListenerContainerFactory();
        listener.setConnectionFactory(connectionFactorySl);
        listener.setConcurrency("1-10");//设置连接数
        listener.setRecoveryInterval(1000L);//重连间隔时间
        listener.setSessionAcknowledgeMode(4);
        return listener;
    }
    //endregion


}
