package com.elvenislove.rabbitmqconfig;



import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.logging.Logger;

/**
 * @author elven
 * @version 1.0.0
 * @date 2020/12/25 15:46
 */
@Configuration
@SpringBootConfiguration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "boot_topic_exchange";
    public static final String QUEUE_NAME = "boot_queue";

    /**
     * 交换机
      */

    @Bean("bootExchange")
    public Exchange bootExchange() {
        return ExchangeBuilder
                .topicExchange(EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    /**Queue 队列
     * @return
     */
    @Bean("bootQueue")
    public Queue bootQueue() {
        return QueueBuilder.durable(QUEUE_NAME).build();

    }

    /** 绑定交换机和队列 Binding
     *
     * @param queue
     * @param exchange
     * @return
     */

    @Bean
    public Binding binQueueExchange(@Qualifier("bootQueue") Queue queue, @Qualifier("bootExchange") Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("boot.#").noargs();

    }
}
