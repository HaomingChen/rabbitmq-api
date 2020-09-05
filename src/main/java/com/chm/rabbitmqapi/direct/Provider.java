package com.chm.rabbitmqapi.direct;

import com.chm.rabbitmqapi.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author Haoming Chen
 * @date 2020/9/3 16:52
 */
public class Provider {
    public static void main(String[] args) throws IOException {

        String exchangeName = "logs_direct";
        //获取连接对象
        Connection connection = RabbitMqUtils.getConnection();
        //获取连接对象
        Channel channel = connection.createChannel();
        //通过通道声明交换机
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
        //发送消息
        String routingKey = "info";
        channel.basicPublish(exchangeName, routingKey, null,("direct message sent to routing key: [" + routingKey + "]").getBytes());
        RabbitMqUtils.closeConnectionAndChannel(channel, connection);

    }
}
