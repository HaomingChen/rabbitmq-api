package com.chm.rabbitmqapi.intergrate.ttl;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
public class TestTTL {

    /**
     * 队列统一过期
     *
     * 设置了消息的过期时间和队列的过期时间以消息的过期时间为准
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "test_queue_ttl",
                            arguments = {@Argument(name = "x-message-ttl", value = "10000", type = "java.lang.Long")}),//不指定名字, 创建临时队列
                    exchange = @Exchange(value = "test_exchange_ttl", type = ExchangeTypes.TOPIC), //绑定的交换机
                    key = {"ttl"}
            ),
    })
    public void onMessage(String message) {
        //获取消息
        System.out.println("ttl message: " + message);
    }

}
