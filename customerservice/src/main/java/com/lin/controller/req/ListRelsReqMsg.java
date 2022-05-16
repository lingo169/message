package com.lin.controller.req;

import com.lin.common.rest.ReqMsg;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ListRelsReqMsg extends ReqMsg {

    @NotNull(message = "客户编号不能为空")
    private Long customerId;
    /**
     * 0：不包含群组
     * 1：群组+好友
     */
    @NotNull(message = "是否包含群组字段不能为空")
    private String incloudGroupFlag;
    @NotNull(message = "页数不能为空")
    private Integer pageNo;
    @NotNull(message = "页码不能为空")
    private Integer pageSize;
}
