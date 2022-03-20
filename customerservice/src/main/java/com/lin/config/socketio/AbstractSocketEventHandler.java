//package com.lin.config.socketio;
//
//import com.corundumstudio.socketio.AckRequest;
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.annotation.OnConnect;
//import com.corundumstudio.socketio.annotation.OnDisconnect;
//import com.lin.dto.socketio.ChatObject;
//import org.redisson.api.RedissonClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.UUID;
//
//public abstract class AbstractSocketEventHandler {
//
//    private static Logger LOG = LoggerFactory.getLogger(AbstractSocketEventHandler.class);
//
//    @Autowired
//    private SocketIOServer socketIOServer;
//
//    @Autowired
//    private ClientUserCache clientUserCache;
//
//    @Autowired
//    private RedissonClient redissonClient;
//
//    public AbstractSocketEventHandler() {
//    }
//
//    public AbstractSocketEventHandler(SocketIOServer socketIOServer) {
//        this.socketIOServer = socketIOServer;
//    }
//
//    /**
//     * 添加connect事件，当客户端发起连接时调用，本文中将clientid与sessionid存入数据库
//     * 方便后面发送消息时查找到对应的目标client,
//     *
//     * @param client
//     */
//    @OnConnect
//    public void onConnect(SocketIOClient client) {
//        LOG.info("Connect OK.");
//        LOG.info("Session ID  : {}", client.getSessionId());
//        LOG.info("HttpHeaders : {}", client.getHandshakeData().getHttpHeaders());
//        LOG.info("UrlParams   : {}", client.getHandshakeData().getUrlParams());
//        /*String token = client.getHandshakeData().getSingleUrlParam("token");
//        RBucket<String> bucket = redissonClient.getBucket(RedisConstant.WDP_TOKEN + token);
//        String data = bucket.get();
//        if (StringUtils.isEmpty(data)) {
//            LOG.info("**********客户端：" + token + "无权限**********");
//            client.disconnect();//校验token示例
//            return;
//        }*/
//        String userId = client.getHandshakeData().getSingleUrlParam("userId");
//        LOG.info("**********客户端：：：" + userId + "你成功的连接上了服务器哦**********");
//        UUID sessionId = client.getSessionId();
//        if (userId != null) {
//            clientUserCache.saveClient(userId, sessionId, client);
//        }
//    }
//
//    public abstract void handler(SocketIOClient client, ChatObject data, AckRequest ackSender);
//
////    @OnEvent("message")
////    public void onEvent(SocketIOClient client, ChatObject data, AckRequest ackSender) {
////        LOG.info("send push_data_event");
////        this.handler(client, data, ackSender);
//////        client.sendEvent("push_data_event", "connection:ok");
////    }
//
//    /**
//     * 添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息
//     *
//     * @param client
//     */
//    @OnDisconnect
//    public void onDisconnect(SocketIOClient client) {
//        LOG.debug("Disconnect OK.");
//        LOG.debug("Session ID  : {}", client.getSessionId());
//        String userId = client.getHandshakeData().getSingleUrlParam("userId");
//        LOG.info("**********客户端：" + userId + "已断开连接**********");
//        if (userId != null) {
//            clientUserCache.deleteSessionClient(userId, client.getSessionId());
//            client.disconnect();
//        }
//    }
//}
