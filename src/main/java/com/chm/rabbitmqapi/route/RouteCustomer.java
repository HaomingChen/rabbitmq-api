package com.chm.rabbitmqapi.route;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RouteCustomer {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//创建临时队列
                    exchange = @Exchange("directs"),// 指定交换机名称和类型, 默认为direct
                    key = {"info", "error", "warn"}
            )
    })
    public void receiver1(String message) {
        System.out.println("message = " + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue,//创建临时队列
                    exchange = @Exchange("directs"),// 指定交换机名称和类型, 默认为direct
                    key = {"info", "error"}
            )
    })
    public void receiver2(String message) {
        System.out.println("message = " + message);
    }

}
