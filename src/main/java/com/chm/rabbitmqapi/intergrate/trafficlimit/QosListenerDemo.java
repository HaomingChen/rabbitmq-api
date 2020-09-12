package com.chm.rabbitmqapi.intergrate.trafficlimit;

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
public class QosListenerDemo {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "test_prefetch_message"),//不指定名字, 创建临时队列
                    exchange = @Exchange(value = "test_prefetch_message", type = ExchangeTypes.TOPIC), //绑定的交换机
                    key = {"qos"}
            ),
    }, ackMode = "MANUAL")
    public void onMessage(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException, InterruptedException {
        //也可以通过配置spring.rabbitmq.listener.simple.prefetch这个配置来配置消费端监听的最大个数
        //获取消息
        channel.basicQos(1);
        System.out.println("prefetch message: " + message);
        channel.basicAck(deliveryTag, true);
    }

}
