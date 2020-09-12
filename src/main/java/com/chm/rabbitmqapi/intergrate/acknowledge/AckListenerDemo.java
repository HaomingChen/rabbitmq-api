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
public class AckListenerDemo {

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "test_manual_ack"),//不指定名字, 创建临时队列
                    exchange = @Exchange(value = "test_manual_ack", type = ExchangeTypes.TOPIC), //绑定的交换机
                    key = {"confirm"}
            ),

    }, ackMode = "MANUAL")
    public void onMessage(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {
        //接收转换消息
        System.out.println("consumerDoAck: " + message);
        //处理业务逻辑
        if (message.contains("confirm")) {
            System.out.println("签收");
            // RabbitMQ的ack机制中，第二个参数返回true，表示需要将这条消息投递给其他的消费者重新消费
            //手动签收
            channel.basicAck(deliveryTag, false);
        } else {
            System.out.println("不签收");
            // 第三个参数true，表示这个消息会重新进入队列
            //手动签收
            channel.basicNack(deliveryTag, true, true);
            //basicReject方法 单条处理, 推荐使用basicAck
//            channel.basicReject(deliveryTag, true);
        }
    }

}
