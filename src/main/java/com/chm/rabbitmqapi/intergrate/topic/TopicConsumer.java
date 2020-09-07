package com.chm.rabbitmqapi.intergrate.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(type = ExchangeTypes.TOPIC, name = "topics"),
                    key = {"user.save", "user.*"}
            )
    })
    public void receiver1(String message) {
        System.out.println("message1 = " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(type = ExchangeTypes.TOPIC, name = "topics"),
                    key = {"order.#", "product.#","user.*"}
            )
    })
    public void receiver2(String message) {
        System.out.println("message2 = " + message);
    }

}
