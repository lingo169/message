package com.lin.controller.req;

import com.lin.common.rest.ReqMsg;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
@Setter
@Getter
public class LifeListReqMsg extends ReqMsg {

    @NotNull(message = "客户不能为空")
    private Long customerId;
    @NotNull(message = "页数不能为空")
    private Integer pageNo;
    @NotNull(message = "页码不能为空")
    private Integer pageSize;
}
