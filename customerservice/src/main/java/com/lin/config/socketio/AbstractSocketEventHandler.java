//package com.lin.config.socketio;
//
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.annotation.OnConnect;
//import com.corundumstudio.socketio.annotation.OnDisconnect;
//import com.lin.common.constant.CommonConstant;
//import com.lin.common.error.CustomRuntimeException;
//import com.lin.common.error.ErrorCode;
//import com.lin.config.RequestUtils;
//import com.lin.dto.socketio.ChatObject;
//import com.lin.po.Customer;
//import com.lin.service.customer.CustomerService;
//import org.redisson.api.RedissonClient;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.atomic.AtomicBoolean;
//
//@Component
//public class AbstractSocketEventHandler {
//
//    private static Logger log = LoggerFactory.getLogger(AbstractSocketEventHandler.class);
//
//    public AbstractSocketEventHandler() {
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
//        log.info("Connect OK.");
//        log.info("Session ID  : {}", client.getSessionId());
//        log.info("HttpHeaders : {}", client.getHandshakeData().getHttpHeaders());
//        log.info("UrlParams   : {}", client.getHandshakeData().getUrlParams());
//        log.info("**********客户端：" + client.getHandshakeData().getSingleUrlParam(CommonConstant.TOKEN) + "你成功的连接上了服务器哦**********");
//        //查看好友关系是否存在
//        Customer c = RequestUtils.getCustomer(client.getHandshakeData().getSingleUrlParam(CommonConstant.TOKEN));
//        if (null == c) {
//            log.error("无权限连接，请登录后再链接。");
//            client.disconnect();
//        } else {
//            joinRoom(client, c);
//        }
//        //Customer c = customerService.relationshipByCustomerId(data.getSender());
//
//    }
//
//    /**
//     * 添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息
//     *
//     * @param client
//     */
//    @OnDisconnect
//    public void onDisconnect(SocketIOClient client) {
//        log.debug("Disconnect OK.");
//        log.debug("Session ID  : {}", client.getSessionId());
//        Customer c = RequestUtils.getCustomer(client.getHandshakeData().getSingleUrlParam(CommonConstant.TOKEN));
//        leaveRoom(client, c);
//        log.info("**********客户端：" + client.getHandshakeData().getSingleUrlParam("userId") + "已断开连接**********");
//    }
//
//    private void joinRoom(SocketIOClient client, Customer c) {
//        c.getCustomerRels().forEach(item -> {
//            log.info("joinRoom is:{}", item.getCustomerGroupId());
//            client.joinRoom(item.getCustomerGroupId() + "");
//        });
//    }
//
//    private void leaveRoom(SocketIOClient client, Customer c) {
//        c.getCustomerRels().forEach(item -> {
//            log.info("leaveRoom data.getRoom():{}", item.getCustomerGroupId());
//            client.leaveRoom(item.getCustomerGroupId() + "");
//        });
//    }
//}
