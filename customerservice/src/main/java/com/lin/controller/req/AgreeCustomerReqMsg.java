package com.lin.controller.req;

import com.lin.common.rest.ReqMsg;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Setter
@Getter
public class AgreeCustomerReqMsg extends ReqMsg {

    @NotNull(message = "客户ID不能为空")
    private Long customerId;    //TODO 是否需要

//    @NotNull(message = "目标客户不能为空")
//    private Long customerRelId;

    @NotNull(message = "所属组不能为空")
    private Long customerGroupId;

    @NotBlank(message = "关系类型不能为空")
    private String relFlag; //0：客户关系，1：群关系

    @NotBlank(message = "状态不能为空")
    private String status;

}
