package com.lin.controller.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class AddCustomerReqMsg {

    @NotNull(message = "客户不能为空")
    private Long customerId;
    @NotNull(message = "目标客户不能为空")
    private Long customerRelId;

}
