package com.lin.config.socketio;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.Transport;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import com.lin.common.constant.CommonConstant;
import com.lin.common.constant.SocketIoConstant;
import com.lin.common.error.CustomRuntimeException;
import com.lin.config.RequestUtils;
import com.lin.dto.socketio.ChatObject;
import com.lin.event.socketio.NamespaceHandler;
import com.lin.listener.socketio.SocketAuthorizationListener;
import com.lin.listener.socketio.SocketExceptionListener;
import com.lin.po.Customer;
import com.lin.properties.SocketioServerProperties;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;

@Configuration
public class SocketioConfig {
    private static final Logger log = LoggerFactory.getLogger(SocketioConfig.class);

    @Autowired
    private NamespaceHandler namespaceHandler;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");//"redis://139.198.176.37:6379"
        return Redisson.create(config);
    }

    @Bean
    public SocketIOServer socketIOServer(SocketioServerProperties config, RedissonClient redissonClient) {
        config.setOrigin(null);   // 注意如果开放跨域设置，需要设置为null而不是"*"
        config.setTransports(Transport.POLLING, Transport.WEBSOCKET);
        SocketAuthorizationListener sal = new SocketAuthorizationListener();
        config.setAuthorizationListener(sal);
        SocketExceptionListener sel = new SocketExceptionListener();
        config.setExceptionListener(sel);
        RedissonStoreFactory redissonStoreFactory = new RedissonStoreFactory(redissonClient);
        config.setStoreFactory(redissonStoreFactory);
        /**
         * If you encounter such error when your netty-socketio server stops and starts
         * java.net.BindException: Address already in use
         */
        SocketConfig socketConfig = config.getSocketConfig();
        socketConfig.setReuseAddress(true);
        //允许最大帧长度
//        config.setMaxFramePayloadLength(1024 * 1024);
//        //允许下最大内容
//        config.setMaxHttpContentLength(1024 * 1024);
        final SocketIOServer server = new SocketIOServer(config);
        return server;

    }

    @Bean
    public SocketIONamespace socketIOEncounter(SocketIOServer server) {
        SocketIONamespace encounter = server.addNamespace(SocketIoConstant.ENCOUNTER_NAMESPACE);
        encounter.addConnectListener(client -> {
            log.info("encounter Connect Session ID  : {}", client.getSessionId());
            log.info("encounter HttpHeaders : {}", client.getHandshakeData().getHttpHeaders());
            log.info("encounter UrlParams   : {}", client.getHandshakeData().getUrlParams());
            log.info("**********客户端：" + client.getHandshakeData().getSingleUrlParam(CommonConstant.TOKEN) + "你成功的连接上了服务器哦**********");
            //查看好友关系是否存在
            Customer c = RequestUtils.getCustomer(client.getHandshakeData().getSingleUrlParam(CommonConstant.TOKEN));
            if (null == c) {
                log.error("无权限连接，请登录后再链接。");
                client.disconnect();
            } else {
                joinRoom(client, c);
            }
        });
        encounter.addEventListener("message", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) throws CustomRuntimeException, InterruptedException, RemotingException, MQClientException, MQBrokerException, UnsupportedEncodingException {
                log.info("encounter send SocketIOClient:{}", client.getSessionId());
                log.info("encounter handler send body :{}", data.toString());
            }
        });
        encounter.addDisconnectListener(client -> {
            log.info("encounter DisconnectListener Session ID  : {}", client.getSessionId());
            log.info("encounter DisconnectListener HttpHeaders : {}", client.getHandshakeData().getHttpHeaders());
            log.info("encounter DisconnectListener UrlParams   : {}", client.getHandshakeData().getUrlParams());
            Customer c = RequestUtils.getCustomer(client.getHandshakeData().getSingleUrlParam(CommonConstant.TOKEN));
            leaveRoom(client, c);
            log.info("**********客户端：" + client.getHandshakeData().getSingleUrlParam("userId") + "已断开连接**********");
        });
        return encounter;
    }

    @Bean
    public SocketIONamespace socketIOChats(SocketIOServer server) {
        SocketIONamespace n = server.addNamespace(SocketIoConstant.CHAT_NAMESPACE);
        n.addConnectListener(client -> {
            log.info("Connect Session ID  : {}", client.getSessionId());
            log.info("HttpHeaders : {}", client.getHandshakeData().getHttpHeaders());
            log.info("UrlParams   : {}", client.getHandshakeData().getUrlParams());
            log.info("**********客户端：" + client.getHandshakeData().getSingleUrlParam(CommonConstant.TOKEN) + "你成功的连接上了服务器哦**********");
            //查看好友关系是否存在
            Customer c = RequestUtils.getCustomer(client.getHandshakeData().getSingleUrlParam(CommonConstant.TOKEN));
            if (null == c) {
                log.error("无权限连接，请登录后再链接。");
                client.disconnect();
            } else {
                joinRoom(client, c);
            }
        });
        n.addEventListener("message", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient client, ChatObject data, AckRequest ackRequest) throws CustomRuntimeException, InterruptedException, RemotingException, MQClientException, MQBrokerException, UnsupportedEncodingException {
                namespaceHandler.event(client, data, ackRequest);
            }
        });
        n.addDisconnectListener(client -> {
            log.info("DisconnectListener Session ID  : {}", client.getSessionId());
            log.info("DisconnectListener HttpHeaders : {}", client.getHandshakeData().getHttpHeaders());
            log.info("DisconnectListener UrlParams   : {}", client.getHandshakeData().getUrlParams());
            Customer c = RequestUtils.getCustomer(client.getHandshakeData().getSingleUrlParam(CommonConstant.TOKEN));
            leaveRoom(client, c);
            log.info("**********客户端：" + client.getHandshakeData().getSingleUrlParam("userId") + "已断开连接**********");
        });
        return n;
    }

    /**
     * 注入OnConnect，OnDisconnect，OnEvent注解。 不写的话Spring无法扫描OnConnect，OnDisconnect等注解
     */
    /*@Bean
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }*/
    private void joinRoom(SocketIOClient client, Customer c) {
        c.getCustomerRels().forEach(item -> {
            log.info("joinRoom is:{}", item.getCustomerGroupId());
            client.joinRoom(item.getCustomerGroupId() + "");
        });
    }

    private void leaveRoom(SocketIOClient client, Customer c) {
        c.getCustomerRels().forEach(item -> {
            log.info("leaveRoom data.getRoom():{}", item.getCustomerGroupId());
            client.leaveRoom(item.getCustomerGroupId() + "");
        });
    }
}
