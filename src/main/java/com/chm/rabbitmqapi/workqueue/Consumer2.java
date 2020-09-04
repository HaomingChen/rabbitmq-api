package com.chm.rabbitmqapi.workqueue;

import com.chm.rabbitmqapi.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @author Haoming Chen
 * @date 2020/9/3 11:02
 */
public class Consumer2 {

    public static void main(String[] args) throws IOException {
        //获取链接对象
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        //每次只能消费一个消息
        channel.basicQos(1);
        channel.queueDeclare("work", true, false, false, null);
        channel.basicConsume("work", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-2: " + new String(body));
                //参数1: 确认队列中哪个具体的消息 参数2: 是否开启多个消息同时确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }

}
