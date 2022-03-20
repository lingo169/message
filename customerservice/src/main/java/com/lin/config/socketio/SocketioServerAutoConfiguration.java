//package com.lin.config.socketio;
//
//import com.corundumstudio.socketio.AuthorizationListener;
//import com.corundumstudio.socketio.HandshakeData;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
//import com.corundumstudio.socketio.listener.ExceptionListener;
//import com.corundumstudio.socketio.store.RedissonStoreFactory;
//import com.corundumstudio.socketio.store.StoreFactory;
//import com.lin.dto.socketio.ChatObject;
//import com.lin.properties.SocketioServerProperties;
//import io.netty.channel.epoll.Epoll;
//import org.apache.commons.lang3.StringUtils;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class SocketioServerAutoConfiguration implements DisposableBean {
//    protected static Logger LOG = LoggerFactory.getLogger(SocketioServerAutoConfiguration.class);
//    public static final String CHAT_EVENT = "chatevent";
//    @Autowired
//    private SocketioServerProperties config;
//
//    @Autowired
//    protected SocketIOServer socketIOServer;
//
//
//    @Bean
//    public RedissonClient redissonClient() {
//        //return new RedissonStoreFactory(Redisson.create(socketioRedissonProperties));
//        return Redisson.create();
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public StoreFactory clientStoreFactory() {
//        //return new MemoryStoreFactory();
//        return new RedissonStoreFactory(redissonClient());
//    }
//
//    @Bean(destroyMethod = "stop")
//    public SocketIOServer socketIOServer(ExceptionListener exceptionListener, StoreFactory clientStoreFactory) {
//
//        // 身份验证ExceptionListener
//        config.setAuthorizationListener(new AuthorizationListener() {
//            @Override
//            public boolean isAuthorized(HandshakeData data) {
//                return true;
////                String token = data.getSingleUrlParam(TOKEN);
////                if (StringUtils.equals(token, "123456")) {
////                    LOG.info("auth is true");
////                    return true;
////                }
////                LOG.info("auth is false");
////                return false;
//            }
//        });
//        config.setExceptionListener(exceptionListener);
//        config.setStoreFactory(clientStoreFactory);
//
//        if (config.isUseLinuxNativeEpoll()
//                && !config.isFailIfNativeEpollLibNotPresent()
//                && !Epoll.isAvailable()) {
//            LOG.warn("Epoll library not available, disabling native epoll");
//            config.setUseLinuxNativeEpoll(false);
//        }
//
//        final SocketIOServer server = new SocketIOServer(config);
//
//        server.addConnectListener(client -> {
//            LOG.info("Connect OK.");
//            LOG.info("Session ID  : {}", client.getSessionId());
//            LOG.info("HttpHeaders : {}", client.getHandshakeData().getHttpHeaders());
//            LOG.info("UrlParams   : {}", client.getHandshakeData().getUrlParams());
//        });
//        server.addDisconnectListener(client -> {
//            LOG.info(client.getHandshakeData().getUrl() + "离开了" + client.getHandshakeData().getAddress() + "地址");
//            client.disconnect();
//        });
//
//        server.addEventListener(CHAT_EVENT, ChatObject.class, (client, data, ackSender) -> {
//            LOG.info("send push_data_event");
//            server.getBroadcastOperations().sendEvent(CHAT_EVENT, "WO FA CHUQU LE ");
//        });
//        server.start();
//        return server;
//    }
//
//    @Bean
//    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
//        return new SpringAnnotationScanner(socketServer);
//    }
//
//
//    @Override
//    public void destroy() throws Exception {
//        if (socketIOServer != null) {
//            socketIOServer.stop();
//        }
//    }
//}
