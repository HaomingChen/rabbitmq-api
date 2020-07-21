package com.chm.rabbitmqapi.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMqProducer {
    public static void main(String[] args) throws Exception {

        //创建一个ConnectionFactory 并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.11.76");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        //通过连接工厂创建连接
        Connection connection = connectionFactory.newConnection();

        //通过connection创建一个channel
        Channel channel = connection.createChannel();

        //通过channel发送数据
        String msg = "Hello RabbitMQ!";
        for(int i = 0; i < 5; i++) {
            channel.basicPublish("", "test001", null, msg.getBytes());
        }
        //记得要关闭相关的链接
        channel.close();
        connection.close();

    }
}