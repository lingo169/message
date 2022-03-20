package com.lin.controller.req;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Setter
@Getter
public class AgreeCustomerReqMsg {

    @NotNull(message = "目标客户不能为空")
    private Long customerRelId;

    @NotNull(message = "目标客户不能为空")
    private Long customerGroupId;

    @NotBlank(message = "状态不能为空")
    private String status;

}
