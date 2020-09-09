package com.chm.rabbitmqapi.intergrate.acknowledge;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AckListener {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "test_manual_ack"),//不指定名字, 创建临时队列
                    exchange = @Exchange(value = "test_manual_ack", type = ExchangeTypes.TOPIC), //绑定的交换机
                    key = {"confirm"}
            ),

    }, ackMode = "MANUAL")
    public void onMessage(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {
        System.out.println("consumerDoAck: " + message);
        if (message.contains("message")) {
            // RabbitMQ的ack机制中，第二个参数返回true，表示需要将这条消息投递给其他的消费者重新消费
            channel.basicAck(deliveryTag, false);
        } else {
            // 第三个参数true，表示这个消息会重新进入队列
            channel.basicNack(deliveryTag, false, true);
        }
    }

}
