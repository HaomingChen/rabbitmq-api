package com.chm.rabbitmqapi.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author Haoming Chen
 * @date 2020/8/21 17:10
 */
public class RabbitMqUtils {

    private static ConnectionFactory connectionFactory;

    static {
        //重量级资源 类加载执行
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/ems");
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");
    }

    ///定义提供链接对象的方法
    public static Connection getConnection() {

        try {
            return connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    //关闭通道和关闭连接工具方法
    public static void closeConnectionAndChannel(Channel channel, Connection conn) {
        try {
            if (channel != null) {
                channel.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
