package com.chm.rabbitmqapi.helloworld;

import com.chm.rabbitmqapi.utils.RabbitMqUtils;
import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Haoming Chen
 * @date 2020/8/21 16:13
 */
public class Consumer {

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

//        //创建连接工厂
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setHost("localhost");
//        connectionFactory.setPort(5672);
//        connectionFactory.setVirtualHost("/ems");
//        connectionFactory.setUsername("ems");
//        connectionFactory.setPassword("123");
//
//        //创建连接对象
//        Connection connection = connectionFactory.newConnection();
        //通过工具类获取连接对象
        Connection connection = RabbitMqUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //通道绑定对象
        channel.queueDeclare("hello", false, false, false, null);
        //消费消息
        //参数1: 消费那个队列的消息 队列名称
        //参数2: 开始消息的自动确认机制
        //参数3: 消费时的回调接口
        channel.basicConsume("hello", true, new DefaultConsumer(channel){
            @Override //最后一个参数body -> 消息队列中取出的消息
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("new String(body) = " + new String(body));
            }
        });
        Thread.sleep(1000);
//        channel.close();
//        connection.close();
        RabbitMqUtils.closeConnectionAndChannel(channel, connection);

    }

}
