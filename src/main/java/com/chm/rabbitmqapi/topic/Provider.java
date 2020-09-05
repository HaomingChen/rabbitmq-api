package com.chm.rabbitmqapi.topic;

import com.chm.rabbitmqapi.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class Provider {

    public static void main(String[] args) throws IOException {

        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机名称及交换机类型 topic
        channel.exchangeDeclare("topics", BuiltinExchangeType.TOPIC);
        //发布消息
        String routingKey = "user.as";
        channel.basicPublish("topics", routingKey, null, ("这里是topic动态路由模型,routeKey:[" + routingKey + "]").getBytes());
        RabbitMqUtils.closeConnectionAndChannel(channel, connection);

    }

}
