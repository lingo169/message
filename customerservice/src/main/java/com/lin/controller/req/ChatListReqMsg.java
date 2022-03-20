package com.lin.controller.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ChatListReqMsg extends ReqMsg{
    /**
     * 用户ID customerid
     */
    @NotBlank(message = "用户名不能为空")
    private Long id;
}
