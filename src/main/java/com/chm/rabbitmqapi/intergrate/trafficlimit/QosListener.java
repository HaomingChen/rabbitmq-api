package com.chm.rabbitmqapi.intergrate.trafficlimit;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * Consumer限流机制
 * 1. 确保ack机制为手动确认(为什么)
 * 2. listener-container配置属性 prefetch = 1
 * 表示消费端每次从mq拉取一条消息来消费，直到手动确认消费完毕后, 才会继续拉取下一条消息
 */
@Component
public class QosListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

    }

}
