package com.lin.consume;

import com.corundumstudio.socketio.SocketIONamespace;
import com.lin.common.constant.CustomerServiceConstant;
import com.lin.dao.CustomerGroupMapper;
import com.lin.dto.socketio.ChatObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChatsRabbitmqConsumer {
    private static Logger log = LoggerFactory.getLogger(ChatsRabbitmqConsumer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.socketio.topic}")
    private String socketioTopic;

    @Value("${spring.rabbitmq.content.topic}")
    private String contentTopic;

    @Autowired
    private SocketIONamespace socketIOChats;

    @Autowired
    private CustomerGroupMapper customerGroupMapper;

    /**
     * #{T(com.lin.common.utils.DateUtils).fullDateFormat()} 动态创建队列，是为了每个微服务都能获取到消息，例如TOPIC
     *
     * @param co 消息内容
     */
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue("#{T(com.lin.common.utils.DateUtils).fullDateFormat()}"),  //系统自行 创建临时队列
                    exchange = @Exchange(value = "#{consumeProperties.getSocketio().get(\"topic\")}", type = CustomerServiceConstant.EXCHANGE_FANOUT)  //绑定的交换机
            )
    })
    public void chatsReceivel(ChatObject co) {
        log.info("rabbitmq ChatsConsumer on message group:{} body :{}", co.getCustomerGroupId(), co.toString());
        ConsumerHandler cc = ConsumerHandler.getBean(socketioTopic + CustomerServiceConstant.CONSUMER_HANDLER);
        cc.onMessage(co);

        //TODO 存储发送消息内容
//        BroadcastOperations bo = socketIOChats.getRoomOperations(co.getCustomerGroupId() + "");
//        bo.getClients().forEach(i -> {
//            log.info("rabbitmq receive Session ID  :{}，AllRooms：{}", i.getSessionId(),i.getAllRooms());
//        });
//        socketIOChats.getRoomOperations(co.getCustomerGroupId() + "").sendEvent(SocketIoConstant.CHAT_EVENT_NAME, co);
//
//        //异步更新群组的俩天信息和事件
//        rabbitTemplate.convertAndSend(consumeProperties.getContent().get(RocketMqConstant.TOPIC),null,co);
        //TODO 异步更新群组的俩天信息和事件
//        try {
//            DefaultMQProducer mqp = CommonProductFactory.getInstance();
//            Message msg = MQMessage.build(consumeProperties.getContent().get(RocketMqConstant.TOPIC), consumeProperties.getContent().get(RocketMqConstant.TAGS), co);
//            mqp.send(msg);
//        } catch (Exception e) {
//            log.error("rocketmq send error", e);
//        }
        log.info("rabbitmq send client success");
    }

    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue("contentUpdate"),  //系统自行 创建临时队列
                    exchange = @Exchange(value = "#{consumeProperties.getContent().get('topic')}")  //绑定的交换机
            )
    })
    public void contentReceivel(ChatObject co) {
        log.info("rabbitmq contentReceivel on message body :{}", co.toString());
        ConsumerHandler cc = ConsumerHandler.getBean(contentTopic + CustomerServiceConstant.CONSUMER_HANDLER);
        cc.onMessage(co);
    }
}
