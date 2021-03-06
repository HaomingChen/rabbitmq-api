package com.chm.rabbitmqapi.helloworld;

import com.chm.rabbitmqapi.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Haoming Chen
 * @date 2020/8/21 14:44
 */
public class Provider {

    //生产消息
    @Test
    public void testSendMessage() throws IOException, TimeoutException {

//        //创建连接mq的连接工厂对象
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        //设置连接rabbitmq主机
//        connectionFactory.setHost("localhost");
//        //设置端口号 -> 作tcp通信
//        connectionFactory.setPort(5672);
//        //设置连接哪个虚拟主机
//        connectionFactory.setVirtualHost("/ems");
//        //设置访问虚拟主机的用户名和密码
//        connectionFactory.setUsername("ems");
//        connectionFactory.setPassword("123");
//        //获取连接对象
//        Connection connection = connectionFactory.newConnection();
        //通过工具类获取连接对象
        Connection connection = RabbitMqUtils.getConnection();
        //获取连接中通道
        Channel channel = connection.createChannel();
        //通道绑定对应消息队列
        //参数1: 队列名称 如果队列不存在自动创建
        //参数2: 用来定义队列特性是否要持久化 true: 持久化队列 false: 不持久化队列
        //参数3: exclusive 是否独占队列 -> 当前队列仅当前连接可用
        //参数4: autoDelete: 是否在消费完成后自动删除队列 -> 是否自动删除队列
        //参数5: 额外附加参数
                                //队列名 //仅为队列持久化
        channel.queueDeclare("hello", true, false, false, null);
        //发布消息
        //参数1: 交换机名称
        //参数2: 队列名称
        //参数3: 传递消息额外设置 -> 可设置队列中的消息持久化
        //参数4: 消息的具体内容
                                //routing key -> 控制发到哪
        channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, "hello rabbitmq".getBytes());
//        channel.close();
//        connection.close();
        RabbitMqUtils.closeConnectionAndChannel(channel, connection);

    }

}
