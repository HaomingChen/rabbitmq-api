package com.example.rabbitmqapi;

import com.chm.rabbitmqapi.Application;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
