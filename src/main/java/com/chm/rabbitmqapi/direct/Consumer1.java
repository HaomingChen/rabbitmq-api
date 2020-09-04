package com.chm.rabbitmqapi.direct;

import com.chm.rabbitmqapi.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author Haoming Chen
 * @date 2020/9/3 17:06
 */
public class Consumer1 {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        //通道声明交换机以及交换机的类型
        channel.exchangeDeclare("logs_direct", BuiltinExchangeType.DIRECT);
        //创建一个临时队列
        String queue = channel.queueDeclare().getQueue();
        //基于routing key绑定队列和交换机
        channel.queueBind(queue, "logs_direct", "error");

    }

}
