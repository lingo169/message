//package com.lin.config;
//
//import com.lin.consume.ConsumerFactory;
//import com.lin.consume.ConsumerHandler;
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
//
//import java.util.List;
//
///**
// * rocketmq消费者单例
// */
//public class CommonConsumerFactory {
//
//    private String consumerGroup;
//    private MessageModel messageModel;
//
//    private static Logger logger = LoggerFactory.getLogger(CommonConsumerFactory.class);
//
//    private enum CommonConsumerSingleton {
//        INSTANCE;
//
//        private DefaultMQPushConsumer instance;
//
//        /**
//         *
//         * @param consumerGroup 消费群组
//         * @param messageModel 消息模式
//         * @param topic 主题.
//         * @param tags  多个tag则是需要把采用"||"进行拼接
//         */
//        private DefaultMQPushConsumer initSingleton(String consumerGroup,MessageModel messageModel,String topic,String tags) {//枚举类的构造方法在类加载是被实例化
//            instance = new DefaultMQPushConsumer(consumerGroup);
//            instance.setNamesrvAddr(PropertyUtil.getString("rocketmq.name-server"));
//            instance.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//            instance.setMessageModel(messageModel); //设置广播消费模式 即同一组每个消费单例都需要消费，这样才能发送各个系统的SocketIOClient
//            try {
//                instance.subscribe(topic, tags);//多个 tag采用这种模式："TagA || TagC || TagD"
//                instance.registerMessageListener(new MessageListenerConcurrently() {
//                    @Override
//                    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//
//                        ConsumerHandler cc= ConsumerFactory.createConsumer(topic);
//                        //ChatsConsumer cc = SpringContextIniter.getBean(ChatsConsumer.class);
//                        for (MessageExt msg : msgs) {
//                            try {
//                                cc.onMessage(msg);
//                            } catch (Exception e) {
//                                logger.error("交易回调异常：" + e.getMessage(), e);
//                            }
//
//                        }
//                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                    }
//                });
//                instance.start();
//                logger.info("Socketmq {} Consumer Success",topic);
//            } catch (MQClientException e) {
//                logger.error("subscribe init error: {}", e);
//            }
//            return instance;
//        }
//
//        public DefaultMQPushConsumer getInstance(String consumerGroup,MessageModel messageModel,String topic,String tags) {
//            if (instance != null && instance.getConsumerGroup().equals(consumerGroup)) {
//                return instance;
//            } else {
//                return initSingleton(consumerGroup,messageModel,topic,tags);
//            }
//
//        }
//    }
//
//    public static DefaultMQPushConsumer getInstance(String consumerGroup,MessageModel messageModel,String topic,String tags) {
//        return CommonConsumerFactory.CommonConsumerSingleton.INSTANCE.getInstance(consumerGroup,messageModel,topic,tags);
//    }
//
//}
