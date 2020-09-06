package com.chm.rabbitmqapi.hello;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component // 持久化 非独占 不自动删除队列
@RabbitListener(queuesToDeclare = @Queue(value = "hello", durable = "false", autoDelete = "true"))
public class HelloConsumer {

    @RabbitHandler
    public void receiver(String message) {
        System.out.println("message = " + message);
    }

}
