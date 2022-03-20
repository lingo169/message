package com.lin.config.socketio;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import com.corundumstudio.socketio.store.StoreFactory;
import com.lin.common.error.CustomRuntimeException;
import com.lin.dto.socketio.ChatObject;
import com.lin.event.socketio.NamespaceHandler;
import com.lin.listener.socketio.SocketAuthorizationListener;
import com.lin.listener.socketio.SocketExceptionListener;
import com.lin.properties.SocketioRedissonProperties;
import com.lin.properties.SocketioServerProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketioConfig {
    private static Logger LOG = LoggerFactory.getLogger(SocketioConfig.class);
    public static final String NAMESPACE_ = "/chat1";//构建命名空间
    @Autowired
    private NamespaceHandler namespaceHandler;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://139.198.176.37:6379");
        return Redisson.create(config);
        //return Redisson.create();
    }

    @Bean
    public StoreFactory clientStoreFactory(RedissonClient redissonClient) {
        //return new MemoryStoreFactory();
        return new RedissonStoreFactory(redissonClient);
        //return new RedissonStoreFactory(Redisson.create(socketioRedissonProperties));
    }

    @Bean
    public SocketIOServer socketIOServer(SocketioServerProperties config) {
        config.setOrigin(null);   // 注意如果开放跨域设置，需要设置为null而不是"*"
        config.setTransports(Transport.POLLING, Transport.WEBSOCKET);
        SocketAuthorizationListener sal =new SocketAuthorizationListener();
        config.setAuthorizationListener(sal);
        SocketExceptionListener sel =new SocketExceptionListener();
        config.setExceptionListener(sel);
        //允许最大帧长度
//        config.setMaxFramePayloadLength(1024 * 1024);
//        //允许下最大内容
//        config.setMaxHttpContentLength(1024 * 1024);
        final SocketIOServer server = new SocketIOServer(config);
        SocketIONamespace n = server.addNamespace(NAMESPACE_);
        n.addEventListener("message", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) throws CustomRuntimeException {
                namespaceHandler.event(server,client, data, ackRequest);
            }
        });

//        n.addConnectListener(client -> {
//            namespaceHandler.connect(client);
//        });
//        n.addEventListener(NamespaceHandler.CHAT_EVENT, ChatObject.class, (client, data, ackRequest)->{
//            namespaceHandler.event(client,data,ackRequest);
//        });
//        n.addDisconnectListener(client -> {namespaceHandler.disconnect(client);});
//        server.start();
        return server;

    }
    /*@Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }*/


}
