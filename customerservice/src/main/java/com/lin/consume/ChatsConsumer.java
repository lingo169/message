package com.lin.consume;

import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.lin.common.constant.CustomerServiceConstant;
import com.lin.common.constant.SocketIoConstant;
import com.lin.config.CommonProductFactory;
import com.lin.config.MQMessage;
import com.lin.dto.socketio.ChatObject;
import com.lin.dto.socketio.MessageBase;
import com.lin.properties.ConsumeProperties;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(RocketMqConstant.CHATS_TOPIC + CustomerServiceConstant.CONSUMER_HANDLER)
public class ChatsConsumer implements ConsumerHandler {
    private static Logger log = LoggerFactory.getLogger(ChatsConsumer.class);
    @Autowired
    private SocketIONamespace socketIOChats;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ConsumeProperties consumeProperties;

    public void onMessage(MessageBase msgExt) {
        ChatObject co = (ChatObject) msgExt;
        log.info("ChatsConsumer on message group:{} body :{}", co.getCustomerGroupId(), co.toString());
        //TODO 存储发送消息内容
        BroadcastOperations bo = socketIOChats.getRoomOperations(co.getCustomerGroupId() + "");
        bo.getClients().forEach(i -> {
            log.info("rabbitmq receive Session ID  :{}，AllRooms：{}", i.getSessionId(), i.getAllRooms());
        });
        socketIOChats.getRoomOperations(co.getCustomerGroupId() + "").sendEvent(SocketIoConstant.CHAT_EVENT_NAME, co);

        //异步更新群组的俩天信息和事件
        rabbitTemplate.convertAndSend(consumeProperties.getContent().get(RocketMqConstant.TOPIC), null, co);
        log.info("send client success");
    }
}
