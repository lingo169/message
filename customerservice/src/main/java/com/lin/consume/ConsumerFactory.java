package com.lin.consume;

import com.lin.config.SpringContextIniter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 生成消费者的处理类
 */
public class ConsumerFactory {
    public static ConsumerHandler createConsumer(String topic) {
        switch (topic) {
            case RocketMqConstant.CHATS_TOPIC:
                return SpringContextIniter.getBean(ChatsConsumer.class);
            case RocketMqConstant.UPDATE_GROUP_TOPIC:
                return SpringContextIniter.getBean(UpdateContentConsumer.class);
            default:
                return null;
        }
    }
}
