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
                    value = @Queue(name = "test_qos_listener"),//不指定名字, 创建临时队列
                    exchange = @Exchange(value = "test_qos_listener", type = ExchangeTypes.TOPIC), //绑定的交换机
                    key = {"qos"}
            ),

    }, ackMode = "MANUAL")
    public void onMessage(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {

    }

}
