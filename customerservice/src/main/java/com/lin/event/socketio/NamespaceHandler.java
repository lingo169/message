package com.lin.event.socketio;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.lin.common.error.CustomRuntimeException;
import com.lin.common.error.ErrorCode;
import com.lin.config.CommonProductFactory;
import com.lin.config.MQMessage;
import com.lin.config.PropertyUtil;
import com.lin.consume.RocketMqConstant;
import com.lin.dto.socketio.ChatObject;
import com.lin.po.Customer;
import com.lin.properties.ConsumeProperties;
import com.lin.service.chats.ChatsService;
import com.lin.service.customer.CustomerService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class NamespaceHandler {
    private static Logger log = LoggerFactory.getLogger(NamespaceHandler.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    ConsumeProperties consumeProperties;

    @Autowired
    private ChatsService chatsService;

    public void event(SocketIOClient client, ChatObject data, AckRequest ackSender) throws CustomRuntimeException, UnsupportedEncodingException, InterruptedException, RemotingException, MQClientException, MQBrokerException {
        log.info("send SocketIOClient:{}", client.getSessionId());
        log.info("handler send body :{}", data.toString());
        try {
            chatsService.save(data);
        } catch (IOException e) {
            log.error("save ChatObject", e);
        }

        /**
         * 发送消息到rabbitmq 为了做socket client做集群用。
         */
        rabbitTemplate.convertAndSend(consumeProperties.getSocketio().get(RocketMqConstant.TOPIC),null,data);

        /**
         * 发送给消息中心， 为了做socket client做集群用。
         */
//        DefaultMQProducer mqp = CommonProductFactory.getInstance();
//        log.info("Socketio Producer map:{},{}",consumeProperties.getSocketio().get(RocketMqConstant.TOPIC),consumeProperties.getSocketio().get(RocketMqConstant.TAGS));
//        Message msg=MQMessage.build(consumeProperties.getSocketio().get(RocketMqConstant.TOPIC), consumeProperties.getSocketio().get(RocketMqConstant.TAGS), data);
//        //Message msg=MQMessage.build(PropertyUtil.getString("cs.socketio.chats.topic"), PropertyUtil.getString("cs.socketio.chats.topic.tag"), data);
//        mqp.send(msg);
        log.info("DefaultMQProducer send success");


    }
}
