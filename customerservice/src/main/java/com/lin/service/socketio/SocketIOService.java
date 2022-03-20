package com.lin.service.socketio;

public interface SocketIOService {
    public void pushMessageToUser(String topic,String userId, String msgContent);

    public void sendToAll(String topic,String msgContent);

    public void sendMessage(String topic, String userId, String msgContent);
}
