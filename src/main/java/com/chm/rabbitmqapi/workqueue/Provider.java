package com.chm.rabbitmqapi.workqueue;

import com.chm.rabbitmqapi.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author Haoming Chen
 * @date 2020/9/3 10:43
 */
public class Provider {

    public static void main(String[] args) throws IOException {

        //获取链接对象
        Connection connection = RabbitMqUtils.getConnection();
        //获取通道对象
        Channel channel = connection.createChannel();
        //通过通道声明队列
        channel.queueDeclare("word", true, false, false, null);
        for (int i = 0; i < 10; i++) {
            //生产消息
            channel.basicPublish("", "work", null, (i + ":hello work queue").getBytes());
        }
        //关闭资源
        RabbitMqUtils.closeConnectionAndChannel(channel, connection);

    }

}
