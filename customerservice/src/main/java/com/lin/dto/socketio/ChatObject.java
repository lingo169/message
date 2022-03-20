package com.lin.dto.socketio;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ChatObject implements Serializable {
    /**
     *
     *客户群编号，来自好友时带过来的群组ID
     */
    private Long customerGroupId;

    /**
     *
     *发出者ID
     */
    private Long sender;

    /**
     *
     * 接收者ID
     */
    private Long recipient;

    /**
     *
     * 消息内容
     */
    private String content;

    /**
     *
     * 文件列表
     */
    private String fileUrl;

    /**
     *
     * 读取标志 0：未读，1：已读，2：撤销，3：删除
     */
    private String readFlag;

    /**
     *
     * 0：客户，1：群
     */
    private String recipientFlag;

//    private String sourceId;
//    private String targetClientId;
//    private String userName;
//    private String message;
//
//    public ChatObject() {
//    }
//
//    public ChatObject(String userName, String message) {
//        super();
//        this.userName = userName;
//        this.message = message;
//    }

}
