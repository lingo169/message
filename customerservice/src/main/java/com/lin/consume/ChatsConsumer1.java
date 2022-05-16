//package com.lin.consume.rocketmq;
//
//import com.corundumstudio.socketio.BroadcastOperations;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.lin.common.constant.SocketIoConstant;
//import com.lin.dto.socketio.ChatObject;
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//@RocketMQMessageListener(topic = "${cs.chats.topic}", consumerGroup = "${rocketmq.consumer.group}")
//public class ChatsConsumer1 implements RocketMQListener<ChatObject>, RocketMQPushConsumerLifecycleListener {
//    /**
//     * 日志
//     */
//    private static Logger log = LoggerFactory.getLogger(ChatsConsumer1.class);
//
//    @Autowired
//    private SocketIOServer socketIOServer;
//    @Override
//    public void onMessage(ChatObject message) {
//        log.info("on message body :{}",message.toString());
//        //TODO 存储发送消息内容
//        BroadcastOperations bo=socketIOServer.getNamespace(SocketIoConstant.NAMESPACE_).getRoomOperations(message.getCustomerGroupId()+"");
//        bo.sendEvent(SocketIoConstant.CHAT_EVENT,message);
//        log.info("send client success");
//    }
//
//    @Override
//    public void prepareStart(DefaultMQPushConsumer consumer) {
//
//    }
//}
