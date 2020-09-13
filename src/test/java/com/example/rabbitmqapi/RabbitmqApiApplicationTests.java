package com.example.rabbitmqapi;

import com.chm.rabbitmqapi.Application;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Correlation;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
class RabbitmqApiApplicationTests {

    //注入rabbit template
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * TTL:过期时间
     * 1. 队列统一过期
     * -> 为队列中的消息配置同意过期
     * 2. 消息单独过期
     */
    @Test
    public void testTtl() {
        //消息的后处理对象, 设置一些消息的参数信息
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) {
                //1. 设置message的信息 -> 设置消息的过期时间
                message.getMessageProperties().setExpiration("1000");
                //2. 返回该消息
                return message;
            }
        };
        //MessagePostProcessor -> 可设置单个消息过期时间
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("test_exchange_ttl", "ttl", "message sent....", messagePostProcessor);
        }
    }

    /**
     * 消费端限流
     */
    @Test
    public void prefetchMessage() {
        //发送消息
        for (int i = 0; i < 20; i++) {
            rabbitTemplate.convertAndSend("test_prefetch_message", "qos", "message sent....");
        }
    }

    /**
     * 测试手动ack
     */
    @Test
    public void testManualConfirm() {
        //发送消息
        rabbitTemplate.convertAndSend("test_manual_ack", "confirm", "message confirm....");
    }

    /**
     * 确认模式:
     * 1. 确认模式开启: ConnectionFactory中开启publisher-confirms="true"(已废弃) -> publisher-confirm-type: correlated
     * 2. 在rabbitTemplate定义ConfirmCallBack回调函数
     */
    @Test
    public void testConfirm() {
        //定义回调
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             * @param correlationData 相关配置信息, 可以在convertAndSend重载方法的参数中设置, 此处可以获取到该信息
             * @param ack exchange交换机 是否成功收到了消息。 true成功， false失败
             * @param cause 失败原因 -> 如ack为false， 该参数将有值, 如失败该参数为null
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("confirm方法被执行了....");
                if (ack) {
                    //接收成功
                    System.out.println("接收成功消息" + cause);
                } else {
                    //接收失败
                    System.out.println("接收失败消息" + cause);
                }
            }
        });
        //发送消息
        rabbitTemplate.convertAndSend("test_exchange_confirm", "confirm", "message confirm....");
    }

    /**
     * 回退模式: 当消息发送给Exchange后， Exchange路由到Queue失败时才会执行ReturnCallBack
     * 1. 确认开启: publisher-returns: true
     * 2. 设置ReturnCallBack
     * 3. 设置Exchange处理消息的模式
     * 1. 如果消息没有路由到Queue, 则丢弃消息(默认)
     * 2. 如果消息没有路由到Queue, 返回给消息发送方ReturnCallBack
     */
    @Test
    public void testReturn() {

        //设置为false -> 丢弃
        //该设置优先于配置生效
        rabbitTemplate.setMandatory(true);
        //设置ReturnCallBack -> 必须设置Exchange处理消息的模式, 否则消息将被丢弃, 且不会执行returnedMessage方法 -> 消息发送成功也不会执行该方法
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println("return执行了");
                System.out.println(replyCode);
                System.out.println(replyText);
                System.out.println(routingKey);
            }
        });
        //发送消息
        rabbitTemplate.convertAndSend("test_exchange_confirm", "confirm", "message confirm....");
    }

    //topic 动态路由, 订阅模式
    @Test
    public void testTopic() {
        rabbitTemplate.convertAndSend("topics", "product.route.add", "user.save 路由消息");
    }

    @Test
    public void testRoute() {
        rabbitTemplate.convertAndSend("directs", "info", "发送info的key的路由信息");
    }

    @Test
    public void testFanout() {
        rabbitTemplate.convertAndSend("logs", "", "Fanout的模型发送的消息");
    }

    //work
    @Test
    public void testWork() {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("work", "work模型");
        }
    }

    @Test
    void testHello() {
        rabbitTemplate.convertAndSend("hello", "hello world");
    }

}
