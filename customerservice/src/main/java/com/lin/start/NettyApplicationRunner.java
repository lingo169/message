package com.lin.start;

import com.corundumstudio.socketio.SocketIOServer;
import com.lin.config.SpringContextIniter;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Configuration
@Component
public class NettyApplicationRunner implements ApplicationRunner {
    protected static Logger LOG = LoggerFactory.getLogger(NettyApplicationRunner.class);

    public static final String NAMESPACE_ = "/chat1";//构建命名空间
    @Autowired
    private SocketIOServer socketIOServer;
    @Autowired
    private RedissonClient redissonClient;
//    @Autowired
//    private NamespaceHandler namespaceHandler;

//
//
//
//    /**
//     * 注入OnConnect，OnDisconnect，OnEvent注解。 不写的话Spring无法扫描OnConnect，OnDisconnect等注解
//     */
//    @Bean
//    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketIOServer) {
//        return new SpringAnnotationScanner(socketIOServer);
//    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //SocketIOServer ss =SocketServerFactory.getInstance();
        socketIOServer.start();
//        socketIOServer.start();
//        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
//        config.setHostname("localhost");
//        config.setPort(9092);
//
//        final SocketIOServer server = new SocketIOServer(config);
//        final SocketIONamespace chat1namespace = server.addNamespace("/chat1");
//        chat1namespace.addEventListener("message", ChatObject.class, new DataListener<ChatObject>() {
//            @Override
//            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
//                // broadcast messages to all clients
//                chat1namespace.getBroadcastOperations().sendEvent("message", data);
//            }
//        });
//
//        final SocketIONamespace chat2namespace = server.addNamespace("/chat2");
//        chat2namespace.addEventListener("message", ChatObject.class, new DataListener<ChatObject>() {
//            @Override
//            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) {
//                // broadcast messages to all clients
//                chat2namespace.getBroadcastOperations().sendEvent("message", data);
//            }
//        });
//
//        server.start();
    }

    @PreDestroy
    private void autoStop() throws Exception {
        LOG.info("stop socketIOServer");
//        SocketIOServer ss =SocketServerFactory.getInstance();
        // 避免重启项目服务端口占用问题
        if (socketIOServer != null) {
            socketIOServer.stop();
        }
        LOG.info("shutdown redissonClient");
        RedissonClient redissonClient= SpringContextIniter.getBean(RedissonClient.class);
        if(redissonClient!=null){
            redissonClient.shutdown();
        }
    }

}
