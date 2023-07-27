package com.kangqing.correlated;

import com.kangqing.simple.PublisherConfirmMessage;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author kangqing
 * @since 2023/7/11 20:37
 */
@Slf4j
@Component
@RabbitListener(queues = CorrelatedConfirmMessage.QUEUE)
public class CorrelatedConsumer {

    @RabbitHandler
    public void onMessage(CorrelatedConfirmMessage<String> message, Channel channel,
                          @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {

        log.info("[CorrelateDonMessage]线程编号 Thread number: {}, 消息内容 message content: {}", Thread.currentThread().getName(), message.toString());
        // 纯手动确认 Just confirm manually
        // ack 确认消息 confirm the message
        // 第二个参数 multiple ，用于批量确认消息，为了减少网络流量，手动确认可以被批处。
        // The second multiple parameter, used to batch confirm messages, in order to reduce network traffic, manual confirmation can be batched.
        // 1. 当 multiple 为 true 时，则可以一次性确认 deliveryTag 小于等于传入值的所有消息
        // 1. When multiple is true, it can be checked once deliveryTag less than all messages
        // 2. 当 multiple 为 false 时，则只确认当前 deliveryTag 对应的消息
        // 2. When multiple is false, only confirm the current deliveryTag corresponding message
        channel.basicAck(deliveryTag, false);
        log.info("手动确认完毕 Manual confirmation completed");
    }
}
