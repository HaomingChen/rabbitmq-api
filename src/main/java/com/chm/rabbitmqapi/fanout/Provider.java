package com.chm.rabbitmqapi.fanout;

import com.chm.rabbitmqapi.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author Haoming Chen
 * @date 2020/9/3 15:44
 */
public class Provider {

    public static void main(String[] args) throws IOException {
        //获取连接对象
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        //将通道声明指定交换机 //参数1: 交换机名称 2: 交换机类型  fanout 广播类型
        channel.exchangeDeclare("logs", BuiltinExchangeType.FANOUT);
        //发送消息             exchange  routing key    是否持久化
        channel.basicPublish("logs","",null, "fanout type message".getBytes());
        RabbitMqUtils.closeConnectionAndChannel(channel,connection);

    }

}
