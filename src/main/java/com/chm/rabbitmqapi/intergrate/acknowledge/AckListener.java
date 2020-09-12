package com.chm.rabbitmqapi.intergrate.acknowledge;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Consumer ACK机制
 * 1. 设置手动签收: acknowledge="manual"
 * 2. 让监听器类实现ChannelAwareMessageListener接口
 * 3. 如果消息成功处理， 则调用channel的basicAck()签收
 * 4. 如果消息处理失败, 则调用channel的basicNack()拒绝签收, broker重新发送欸consumer
 */
@Component
public class AckListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws IOException {

        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        //1. 接收转换消息
        System.out.println(new String(message.getBody()));
        //2. 处理业务逻辑
        System.out.println("处理业务逻辑...");
        //3. 手动签收
        try {
            channel.basicAck(deliveryTag, true);
        } catch (IOException e) {
            //4. 拒绝签收
            channel.basicNack(deliveryTag, true, true);
        }

    }

}
