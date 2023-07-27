package com.kangqing.simple;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author kangqing
 * @since 2023/7/11 16:08
 */
@Slf4j
@Component
@RabbitListener(queues = PublisherConfirmMessage.QUEUE)
public class PublisherConfirmConsumer {

    @RabbitHandler
    public void onMessage(PublisherConfirmMessage<String> message) {
        log.info("[SimplEonMessage]线程编号 thread number: {}, 消息内容 message content: {}", Thread.currentThread().getName(), message.toString());
    }
}
