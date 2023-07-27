package com.kangqing.simple;

import com.rabbitmq.client.ConfirmCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author kangqing
 * @since 2023/7/11 16:04
 */
@Slf4j
@Component
public class PublisherConfirmProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void syncSend(Integer id) {
        // 创建 Create a Demo13Message 消息 message
        PublisherConfirmMessage<String> message = new PublisherConfirmMessage<>();
        message.setId(id);
        message.setData("同步发布确认 Synchronous release confirmation");
        // 同步发送消息 Send messages synchronously
        rabbitTemplate.invoke(new RabbitOperations.OperationsCallback<Object>() {

            @Override
            public Object doInRabbit(RabbitOperations operations) {
                // 同步发送消息
                operations.convertAndSend(PublisherConfirmMessage.EXCHANGE, PublisherConfirmMessage.ROUTING_KEY, message);
                log.info("[doInRabbit][发送消息完成 send message complete]");
                // 等待确认 Waiting for confirmation
                operations.waitForConfirms(0); // timeout 参数，如果传递 0 ，表示无限等待 parameter, if 0 is passed, it means infinite wait
                log.info("[doInRabbit][等待 Wait for Confirm 完成 to complete]");
                return null;
            }

        }, new ConfirmCallback() {

            @Override
            public void handle(long deliveryTag, boolean multiple) throws IOException {
                log.info("[handle][Confirm 成功 success]");
            }

        }, new ConfirmCallback() {

            @Override
            public void handle(long deliveryTag, boolean multiple) throws IOException {
                log.info("[handle][Confirm 失败 failure]");
            }

        });

    }
}
