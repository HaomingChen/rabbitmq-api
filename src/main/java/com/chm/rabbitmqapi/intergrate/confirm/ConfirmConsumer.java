package com.chm.rabbitmqapi.intergrate.confirm;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ConfirmConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "test_queue_confirm"),//不指定名字, 创建临时队列
                    exchange = @Exchange(value = "test_exchange_confirm", type = ExchangeTypes.DIRECT), //绑定的交换机
                    key = {"confirm"}
            )
    })
    public void receiver1(String message) {
        System.out.println("message = " + message);
    }

}
