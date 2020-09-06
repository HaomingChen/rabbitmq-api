package com.chm.rabbitmqapi.intergrate.fanout;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FanoutConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//不指定名字, 创建临时队列
                    exchange = @Exchange(value = "logs", type = ExchangeTypes.FANOUT) //绑定的交换机
            )
    })
    public void receiver1(String message) {
        System.out.println("message = " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//不指定名字, 创建临时队列
                    exchange = @Exchange(value = "logs", type = ExchangeTypes.FANOUT) //绑定的交换机
            )
    })
    public void receiver2(String message) {
        System.out.println("message = " + message);
    }

}
