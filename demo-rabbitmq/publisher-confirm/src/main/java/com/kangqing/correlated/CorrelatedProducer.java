package com.kangqing.correlated;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author kangqing
 * @since 2023/7/11 20:33
 */
@Slf4j
@Component
public class CorrelatedProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id) {
        // 创建 Create Demo13Message 消息 message
        CorrelatedConfirmMessage<String> message = new CorrelatedConfirmMessage<>();
        message.setId(id);
        message.setData("异步发送消息 send a message !!!");
        // 异步发送消息 send a message
        rabbitTemplate.convertAndSend(CorrelatedConfirmMessage.EXCHANGE, CorrelatedConfirmMessage.ROUTING_KEY, message);
        log.info("[AsyncSend][发送消息完成 send message complete]");
    }
}
