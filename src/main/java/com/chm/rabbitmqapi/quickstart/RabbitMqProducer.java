package com.chm.rabbitmqapi.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqProducer {

    private final static String QUEUE_NAME = "HELLO";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String message = "Rabbit is very cute animal.";
        channel.basicPublish("",QUEUE_NAME, null, message.getBytes());
        System.out.println("[x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }

}