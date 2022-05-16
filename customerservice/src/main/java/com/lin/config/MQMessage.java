package com.lin.config;

import com.google.gson.Gson;
import com.lin.dto.socketio.ChatObject;
import com.lin.dto.socketio.MessageBase;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public interface MQMessage {
    static Message build(String topic, String tag, ChatObject t) throws UnsupportedEncodingException {
        if (null == t) {
            return null;
        }
        Gson g = new Gson();
        return new Message(topic, tag, g.toJson(t).getBytes(RemotingHelper.DEFAULT_CHARSET));
    }

//    static ChatObject convert(MessageBase msg) {
//        Gson g = new Gson();
//        return g.fromJson(new String(msg.getBody(), StandardCharsets.UTF_8), ChatObject.class);
//    }
}
