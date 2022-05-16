package com.lin.listener.socketio;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ExceptionListenerAdapter;
import com.lin.common.constant.SocketIoConstant;
import com.lin.dto.socketio.ChatObject;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SocketExceptionListener extends ExceptionListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(SocketExceptionListener.class);

    @Override
    public void onEventException(Exception e, List<Object> args, SocketIOClient client) {
        log.error(e.getMessage(), e);
        ChatObject co=new ChatObject();
        co.setContent(e.getMessage());
        //client.getNamespace().getBroadcastOperations().sendEvent(SocketIoConstant.CHAT_EVENT, co);
        client.sendEvent(SocketIoConstant.CHAT_EVENT_NAME, co);
    }

    @Override
    public void onDisconnectException(Exception e, SocketIOClient client) {
        log.error(e.getMessage(), e);
    }

    @Override
    public void onConnectException(Exception e, SocketIOClient client) {
        log.error(e.getMessage(), e);
    }

    @Override
    public void onPingException(Exception e, SocketIOClient client) {
        log.error(e.getMessage(), e);
    }

    @Override
    public boolean exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        log.error(e.getMessage(), e);
        return true;
    }

}