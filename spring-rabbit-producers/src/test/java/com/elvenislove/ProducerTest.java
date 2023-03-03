package com.elvenislove;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-rabbitmq-producer.xml"})
public class ProducerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 确认模式
     * @throws Exception
     *  开启：1.ConnectionFactory中开启publisher-confirms = "TRUE"确认模式
     *      2.在rabbitTemplate中定义confirmCallback回调函数
     */
    @Test
    public void testSend() throws Exception {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {

            }
        });
    }
}
