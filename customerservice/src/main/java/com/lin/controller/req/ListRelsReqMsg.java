package com.lin.controller.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ListRelsReqMsg extends ReqMsg {

    @NotNull(message = "客户不能为空")
    private Long customerId;
}
