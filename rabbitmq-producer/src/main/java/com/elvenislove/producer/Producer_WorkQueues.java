package com.elvenislove.producer;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**
 * @author elven
 * @version 1.0.0
 * @date 2020/12/25 15:59
 * 发送消息
 */
public class Producer_WorkQueues {
    public static void main(String[] args) throws IOException, TimeoutException {

        // 建立连接工厂
        ConnectionFactory factory_work = new ConnectionFactory();

        // 设置参数IP
        factory_work.setHost("127.0.0.1");
        // 默认端口 ：5672
        factory_work.setPort(1099);
        // 虚拟机默认值：/
        factory_work.setVirtualHost("/");
        // 用户名 默认值：guest
        factory_work.setUsername("guest");
        // 密码 默认值：guest
        factory_work.setPassword("guest");
        // 创建链接
        Connection conn = factory_work.newConnection();
        // 创建channel
        Channel channel = conn.createChannel();
        /**
         * channel.queueDeclare - 队列声明
         *  - 参数：
         *    - queue:队列名称
         *    - declare :是否持久化
         *    - exclusive:是否独占，只能有一个消费者监听这个队列
*             - autoDelete:是否自动删除
         *    - 最大容量
         *    - 最大存量
         *    - 如果没有队列叫这个名字的队列，就会创建，反之不会
         */
        channel.queueDeclare("work_queues", true, false, false, null);

        /**
         * 发送信息
         * exchange - 交换机名称
         * routingKey - 路由键（名称）
         * properties - 消息属性
         * body - 消息体
         * message - 消息体
         */
        channel.basicPublish("", "work_queues", null, "Hello World".getBytes());


        // 释放资源
        channel.close();
        conn.close();


    }
}
