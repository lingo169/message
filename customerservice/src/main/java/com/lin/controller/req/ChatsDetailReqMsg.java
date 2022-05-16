package com.lin.controller.req;

import com.lin.common.rest.ReqMsg;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChatsDetailReqMsg extends ReqMsg {
    @NotNull(message = "组编号不能为空")
    private Long customerGroupId;
    @NotNull(message = "页数不能为空")
    private Integer pageNo;
    @NotNull(message = "页码不能为空")
    private Integer pageSize;
}
