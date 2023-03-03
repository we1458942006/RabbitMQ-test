package com.elvenislove.producer;


import com.rabbitmq.client.BuiltinExchangeType;
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
public class Producer_Topics {
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
        Connection connection = factory_work.newConnection();
        // 创建channel
        Channel channel = connection.createChannel();
        // 创建交换机
        /**
         * exchange：交换机名称
         * type：交换机类型
         *  - DIRECT：直连交换机,定向
         *  - TOPIC：主题交换机，通配符的方式
         *  - FANOUT：广播交换机，发送到每一个与之绑定的队列中
         *  - HEADERS：头部交换机，参数匹配
         * durable：是否持久化
         * autoDelete：是否自动删除
         * arguments：参数
         * internal：是否内部
         * Map<String, Object> arguments：参数
         * queueDeclare：队列声明
         */
        String exchangeName = "test_topic_exchange";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.TOPIC, true, false, null);
        // 创建队列
        String queue1Name = "test_topic_exchange_1";
        String queue2Name = "test_topic_exchange_2";
        String queue3Name = "test_topic_exchange_3";
        // 队列声明
        channel.queueDeclare(queue1Name, true, false, false, null);
        channel.queueDeclare(queue2Name, true, false, false, null);
        channel.queueDeclare(queue3Name, true, false, false, null);
        // 绑定交换机和队列
        /**
         * queue：队列名称
         * exchange：交换机名称
         * routingKey：路由键：系统的名称，日志的级别
         *  -如果routingKey为空，则使用默认路由键,为空则使用队列名称作为路由键
         *  -如果routingKey不为空，则使用指定的路由键
         */
        // 绑定 error
        channel.queueBind(queue1Name, exchangeName, "#.error");
        // 绑定 info error warning
        channel.queueBind(queue2Name, exchangeName, "#.info");
        channel.queueBind(queue2Name, exchangeName, "order.*");
        channel.queueBind(queue2Name, exchangeName, "#.warning.*");
        // 绑定success
        channel.queueBind(queue3Name, exchangeName, "*.*");

        String body = "Hello RabbitMQ";
        // 发布消息
        channel.basicPublish(exchangeName, "", null, body.getBytes());
        // 释放资源
        channel.close();
        connection.close();

    }
}
