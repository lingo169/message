//package com.lin.config.socketio;
//
//import com.corundumstudio.socketio.AckRequest;
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIONamespace;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.listener.DataListener;
//import com.lin.common.constant.RedisConstant;
//import com.lin.config.SpringContextIniter;
//import com.lin.dto.socketio.ChatObject;
//import com.lin.properties.SocketioServerProperties;
//import org.apache.commons.lang3.StringUtils;
//import org.redisson.api.RBucket;
//import org.redisson.api.RedissonClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.UUID;
//
//public class SocketServerFactory {
//    private static Logger LOG = LoggerFactory.getLogger(SocketServerFactory.class);
//    public static final String NAMESPACE_ = "chat1";//构建命名空间
//    private enum Singleton{
//        socketServerFactory;
//
//        private SocketIOServer instance;
//        Singleton(){
//            instance=init();
//        }
//
//        public SocketIOServer getInstance(){
//            return instance;
//        }
//
//        private SocketIOServer init(){
//            ClientUserCache clientUserCache=SpringContextIniter.getBean(ClientUserCache.class);
//            RedissonClient redissonClient= SpringContextIniter.getBean(RedissonClient.class);
//            SocketioServerProperties config=SpringContextIniter.getBean(SocketioServerProperties.class);
////            config.setOrigin(null);   // 注意如果开放跨域设置，需要设置为null而不是"*"
////            config.setTransports(Transport.POLLING, Transport.WEBSOCKET);
//            final SocketIOServer server = new SocketIOServer(config);
//            final SocketIONamespace chat1namespace = server.addNamespace("/chat1");
//            chat1namespace.addConnectListener(client -> {
//                LOG.info("Connect OK.");
//                LOG.info("Session ID  : {}", client.getSessionId());
//                LOG.info("HttpHeaders : {}", client.getHandshakeData().getHttpHeaders());
//                LOG.info("UrlParams   : {}", client.getHandshakeData().getUrlParams());
//                /*String token = client.getHandshakeData().getSingleUrlParam("token");
//                RBucket<String> bucket = redissonClient.getBucket(RedisConstant.WDP_TOKEN + token);
//                String data = bucket.get();
//                if (StringUtils.isEmpty(data)) {
//                    LOG.info("**********客户端：" + token + "无权限**********");
//                    client.disconnect();//校验token示例
//                    return;
//                }*/
//                String userId = client.getHandshakeData().getSingleUrlParam("userId");
//                LOG.info("**********客户端：：：" + userId + "你成功的连接上了服务器哦**********");
//                UUID sessionId = client.getSessionId();
//                if (userId != null) {
//                    clientUserCache.saveClient(userId, sessionId, client);
//                }
//            });
//            chat1namespace.addEventListener("message", ChatObject.class, new DataListener<ChatObject>() {
//                @Override
//                public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
//                    // broadcast messages to all clients
//                    chat1namespace.getBroadcastOperations().sendEvent("message", data);
//                }
//            });
//            chat1namespace.addDisconnectListener(client -> {
//                LOG.debug("Disconnect OK.");
//                LOG.debug("Session ID  : {}", client.getSessionId());
//                String userId = client.getHandshakeData().getSingleUrlParam("userId");
//                LOG.info("**********客户端：" + userId + "已断开连接**********");
//                if (userId != null) {
//                    clientUserCache.deleteSessionClient(userId, client.getSessionId());
//                    client.disconnect();
//                }
//            });
//            return server;
//        }
//    }
//
//    public static SocketIOServer getInstance(){
//        return Singleton.socketServerFactory.getInstance();
//    }
//}
