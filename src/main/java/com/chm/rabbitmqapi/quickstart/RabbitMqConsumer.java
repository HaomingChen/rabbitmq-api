package com.chm.rabbitmqapi.quickstart;

import com.rabbitmq.client.*;

import java.io.IOException;

public class RabbitMqConsumer extends DefaultConsumer {

    public RabbitMqConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleConsumeOk(String consumerTag) {
        super.handleConsumeOk(consumerTag);
    }

    @Override
    public void handleCancelOk(String consumerTag) {
        super.handleCancelOk(consumerTag);
    }

    @Override
    public void handleCancel(String consumerTag) throws IOException {
        super.handleCancel(consumerTag);
    }

    @Override
    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
        super.handleShutdownSignal(consumerTag, sig);
    }

    @Override
    public void handleRecoverOk(String consumerTag) {
        super.handleRecoverOk(consumerTag);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println(body);
    }

    @Override
    public Channel getChannel() {
        return super.getChannel();
    }

    @Override
    public String getConsumerTag() {
        return super.getConsumerTag();
    }

}
