//package com.lin.config;
//
//import com.lin.consume.ConsumerHandler;
//import com.lin.consume.RocketMqConstant;
//import com.lin.dto.socketio.ChatObject;
//import com.lin.properties.ConsumeProperties;
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.client.exception.MQClientException;
//import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class ConsumerConfiguration {
//    private static Logger logger = LoggerFactory.getLogger(ConsumerConfiguration.class);
//
//    @Autowired
//    ConsumeProperties consumeProperties;
//
//    @PostConstruct
//    public void init() throws MQClientException {
//        Map<String,String> map=consumeProperties.getSocketio();
//        logger.info("Socketio Consumer map:{},{},{}",map.get(RocketMqConstant.CONSUMER_GROUP),map.get(RocketMqConstant.TOPIC),map.get(RocketMqConstant.TAGS));
//        build(map, MessageModel.CLUSTERING);
//        Map<String,String> contentMap=consumeProperties.getContent();
//        logger.info("Content Consumer map:{},{},{}",contentMap.get(RocketMqConstant.CONSUMER_GROUP),contentMap.get(RocketMqConstant.TOPIC),contentMap.get(RocketMqConstant.TAGS));
//        build(contentMap, MessageModel.CLUSTERING);
//    }
////    @Bean
////    public DefaultMQPushConsumer SocketioConsumer(ConsumeProperties consumeProperties) throws MQClientException {
////        return build(consumeProperties.getSocketio(), MessageModel.CLUSTERING);
////    }
////
////    @Bean
////    public DefaultMQPushConsumer ContentConsumer(ConsumeProperties consumeProperties) throws MQClientException {
////        return build(consumeProperties.getContent(), MessageModel.CLUSTERING);
////    }
//
//    private DefaultMQPushConsumer build(Map<String, String> map, MessageModel messageModel) throws MQClientException {
//        DefaultMQPushConsumer instance = new DefaultMQPushConsumer(map.get(RocketMqConstant.CONSUMER_GROUP));
//        instance.setNamesrvAddr(PropertyUtil.getString("rocketmq.name-server"));
//        instance.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//        instance.setMessageModel(messageModel); //设置广播消费模式 即同一组每个消费单例都需要消费，这样才能发送各个系统的SocketIOClient
//        instance.subscribe(map.get(RocketMqConstant.TOPIC), map.get(RocketMqConstant.TAGS));//多个 tag采用这种模式："TagA || TagC || TagD"
//        instance.registerMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//                //ConsumerHandler cc = ConsumerFactory.createConsumer(map.get(RocketMqConstant.TOPIC));
//                ConsumerHandler cc = ConsumerHandler.getBean(map.get(RocketMqConstant.TOPIC));
//                logger.info("ConsumerHandler bean name: {}",cc.getClass().getSimpleName());
//                for (MessageExt msg : msgs) {
//                    try {
//                        logger.info("ConsumerHandler message {}",msgs);
//                        cc.onMessage(msg);
//                    } catch (Exception e) {
//                        logger.error("交易回调异常：" + e.getMessage(), e);
//                    }
//
//                }
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });
//        instance.start();
//        logger.info("Socketmq {} Consumer Success", map.get(RocketMqConstant.TOPIC));
//        return instance;
//    }
//}
