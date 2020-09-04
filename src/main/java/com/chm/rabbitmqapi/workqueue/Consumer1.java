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
 * @date 2020/9/3 10:58
 */
public class Consumer1 {

    public static void main(String[] args) throws IOException {

        //获取链接对象
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        //每次只能消费一个消息
        channel.basicQos(1);
        channel.queueDeclare("work", true, false, false, null);
        //参数一: 队列名称 参数二: 消息自动确认
        channel.basicConsume("work", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-1: " + new String(body));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });


    }

}
