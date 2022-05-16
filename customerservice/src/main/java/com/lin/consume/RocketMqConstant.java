package com.lin.consume;

public class RocketMqConstant {

    //防止被NEW
    private RocketMqConstant() {
    }

    public static final String CONSUMER_GROUP = "consumergroup";
    public static final String TOPIC = "topic";
    public static final String TAGS = "tags";

    public static final String CHATS_TOPIC = "message";  //消息发送的TOPIC 对应 rocketmq.consume.topic.socketio.topic=message

    /**
     * 更新Content内容的消息TOPIC
     */
    public static final String UPDATE_GROUP_TOPIC = "content";  //消息发送的TOPIC 对应  rocketmq.consume.topic.content.topic=content
}
