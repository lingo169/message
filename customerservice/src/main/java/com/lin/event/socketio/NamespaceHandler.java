package com.lin.event.socketio;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.lin.common.constant.SocketIoConstant;
import com.lin.common.error.CustomRuntimeException;
import com.lin.config.socketio.SocketioConfig;
import com.lin.dto.socketio.ChatObject;
import com.lin.service.chats.ChatsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class NamespaceHandler {
    private static Logger LOG = LoggerFactory.getLogger(NamespaceHandler.class);


    @Autowired
    private ChatsService chatsService;

    public void event(SocketIOServer socketIOServer,SocketIOClient client, ChatObject data, AckRequest ackSender) throws CustomRuntimeException {
        LOG.info("send push_data_event");
        LOG.info("handler Sender :{},Recipient is:{}", data.getSender(),data.getRecipient());
        //chatsService.save(data);
        data.setContent(data.getContent() + ":" + new Date().getTime());
        socketIOServer.getNamespace(SocketioConfig.NAMESPACE_).getBroadcastOperations().sendEvent(SocketIoConstant.CHAT_EVENT, data);
    }

}
