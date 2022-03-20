//package com.lin.event.socketio;
//
//import com.corundumstudio.socketio.AckRequest;
//import com.corundumstudio.socketio.SocketIOClient;
//import com.corundumstudio.socketio.SocketIOServer;
//import com.corundumstudio.socketio.listener.DataListener;
//import com.google.gson.Gson;
//import com.lin.config.socketio.AbstractSocketEventHandler;
//import com.lin.dto.socketio.ChatObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//
//@Component
//@ConditionalOnClass(SocketIOServer.class)
//public class SocketEventHandler extends AbstractSocketEventHandler {
//    private static Logger LOG = LoggerFactory.getLogger(SocketEventHandler.class);
//    public static final String CHAT_EVENT = "chatevent";
//
//    @Autowired
//    private SocketIOServer socketIOServer;
//
//    @Override
//    public void handler(SocketIOClient client, ChatObject data, AckRequest ackSender) {
//        LOG.info("handler some :" + data.getUserName());
//        data.setMessage(data.getMessage() + ":" + new Date().getTime());
//        socketIOServer.getBroadcastOperations().sendEvent(CHAT_EVENT, data);
//    }
//}
