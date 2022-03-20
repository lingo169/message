//package com.lin.service.socketio;
//
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.lin.config.socketio.ClientUserCache;
//import com.lin.properties.SocketioServerProperties;
//import com.lin.properties.SocketioRedissonProperties;
//import com.lin.dto.socketio.SocketIOMessageDTO;
//import org.apache.commons.lang3.StringUtils;
//import org.redisson.api.RTopic;
//import org.redisson.api.RedissonClient;
//import org.redisson.api.listener.MessageListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.Objects;
//import java.util.UUID;
//
//@Component
//public class SocketIOServiceImpl implements SocketIOService {
//    private static Logger log = LoggerFactory.getLogger(SocketIOServiceImpl.class);
//
//    @Autowired
//    private SocketIOServer socketIOServer;
//
//    @Autowired
//    private ClientUserCache clientUserCache;
//
//    @Autowired(required = false)
//    RedissonClient redissonClient;
//
//    @Autowired(required = false)
//    SocketioRedissonProperties socketioRedissonProperties;
//
//    @Autowired
//    SocketioServerProperties socketioServerProperties;
//
//    /**
//     * 广播（群发）前缀
//     */
//    private static final String MASS_PREFIX = "/mass";
//    /**
//     * socketio
//     */
//    private static final String TOPIC_SOCKETIO_SINGLE = "socketio:single";
//    private static final String TOPIC_SOCKETIO_TOALL = "socketio:toAll";
//
//    @Override
//    public void pushMessageToUser(String topic, String userId, String msgContent) {
//        HashMap<UUID, SocketIOClient> userClient = clientUserCache.getUserClient(userId);
//        if (userClient == null) {
//            log.debug("没有在线的用户");
//            return;
//        }
//        userClient.forEach((uuid, socketIOClient) -> {
//            //向客户端推送消息
//            socketIOClient.sendEvent(topic, msgContent);
//        });
//    }
//
//    @Override
//    public void sendToAll(String topic, String msgContent) {
//        if (StringUtils.isBlank(topic)) {
//            topic = MASS_PREFIX + "/toAll";
//        }
//        socketIOServer.getBroadcastOperations().sendEvent(topic, msgContent);
//    }
//
//    @Override
//    public void sendMessage(String topic, String userId, String msgContent) {
//
//        SocketIOMessageDTO socketIOMessageDTO = new SocketIOMessageDTO(topic, userId, msgContent);
//
//        if (StringUtils.isNotBlank(socketIOMessageDTO.getUserId())) {
//            if (!Objects.isNull(socketioRedissonProperties)) {
//                RTopic rTopic = redissonClient.getTopic(TOPIC_SOCKETIO_SINGLE);
//                rTopic.publish(socketIOMessageDTO);
//            } else {
//                pushMessageToUser(socketIOMessageDTO.getTopic(), socketIOMessageDTO.getUserId(), socketIOMessageDTO.getMsgContent());
//            }
//        } else {
//            sendToAll(socketIOMessageDTO.getTopic(), socketIOMessageDTO.getMsgContent());
//        }
//    }
//
//    @PostConstruct
//    public void init() {
//        if (redissonClient == null) {
//            return;
//        }
//        RTopic topic = redissonClient.getTopic(TOPIC_SOCKETIO_SINGLE);
//        topic.addListener(SocketIOMessageDTO.class, new MessageListener<SocketIOMessageDTO>() {
//            @Override
//            public void onMessage(CharSequence channel, SocketIOMessageDTO socketIOMessageDTO) {
//                socketIOMessageDTO.setMsgContent(socketioServerProperties.getPort() + " : " + socketIOMessageDTO.getMsgContent());
//                if (StringUtils.isNotBlank(socketIOMessageDTO.getUserId())) {
//                    pushMessageToUser(socketIOMessageDTO.getTopic(), socketIOMessageDTO.getUserId(), socketIOMessageDTO.getMsgContent());
//                    log.info("{} {} {}", socketIOMessageDTO.getTopic(), socketIOMessageDTO.getUserId(), socketIOMessageDTO.getMsgContent());
//                }
//            }
//        });
//    }
//}