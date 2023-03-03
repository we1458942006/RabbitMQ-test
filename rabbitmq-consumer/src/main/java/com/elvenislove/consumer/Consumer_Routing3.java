package com.elvenislove.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer_Routing3 {
    public static void main(String[] args) throws IOException, TimeoutException {
        // 建立连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        // 设置参数IP.默认：localhost
        factory.setHost("127.0.0.1");
        // 默认端口 ：5672
        factory.setPort(1099);
        // 虚拟机默认值：/
        factory.setVirtualHost("/");
        // 用户名 默认值：guest
        factory.setUsername("guest");
        // 密码 默认值：guest
        factory.setPassword("guest");
        // 创建链接
        Connection connection = factory.newConnection();
        // 创建channel
        Channel channel = connection.createChannel();

        String queue1Name = "test_direct_1";
        String queue2Name = "test_direct_2";
        String queue3Name = "test_direct_3";

        Consumer consumer = new DefaultConsumer(channel) {
            /**
             * 当收到消息时，会自动调用这个方法
             * @param consumerTag
             * @param envelope
             * @param properties
             * consumerTag:消费者标签
             * envelope:消息体
             * properties:消息属性
             */
            /**
             * No-op implementation of {@link Consumer#handleDelivery}.
             *
             * @param consumerTag
             * @param envelope
             * @param properties
             * @param body
             * 回调方法，当收到消息后，会自动执行该方法
             * consumerTag:消费者标签
             * envelope:消息体,获取一些消息，交换机，路由key....
             * properties:消息属性
             */
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("ConsumerTag" + consumerTag);
                System.out.println("Envelope" + envelope);
                System.out.println("Properties" + properties);
                System.out.println("RoutingKey" + envelope.getRoutingKey());
                System.out.println("Body" + new String(body));
            }
        };
        channel.basicConsume(queue2Name, true, consumer);


        // 关闭资源


    }
}

