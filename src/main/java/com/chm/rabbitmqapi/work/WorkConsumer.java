package com.chm.rabbitmqapi.work;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener
public class WorkConsumer {

    //消费者1
    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void receiver1(String message) {
        System.out.println("message1 = " + message);
    }

    //消费者2
    @RabbitListener(queuesToDeclare = @Queue("work"))
    public void receiver2(String message) {
        System.out.println("message2 = " + message);
    }

}
