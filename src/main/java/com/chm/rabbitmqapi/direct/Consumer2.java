package com.chm.rabbitmqapi.direct;

import com.chm.rabbitmqapi.utils.RabbitMqUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author Haoming Chen
 * @date 2020/9/3 17:06
 */
public class Consumer2 {

    public static void main(String[] args) throws IOException {

        String exchangeName = "logs_direct";
        Connection connection = RabbitMqUtils.getConnection();
        Channel channel = connection.createChannel();
        //通道声明交换机以及交换机的类型
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
        //创建一个临时队列
        String queue = channel.queueDeclare().getQueue();
        //基于routing key绑定队列和交换机
        channel.queueBind(queue, exchangeName, "error");
        channel.queueBind(queue, exchangeName, "info");
        channel.queueBind(queue, exchangeName, "warning");
        //获取消费的消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        });

    }

}
