package com.lin.dto.socketio;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString
public class ChatObject extends MessageBase {
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
     *请求时是base64,响应是url
     * 文件列表
     */
    private String file;

    /**
     * file 和 suffix 同时存在.
     */
    private String suffix;
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


}
