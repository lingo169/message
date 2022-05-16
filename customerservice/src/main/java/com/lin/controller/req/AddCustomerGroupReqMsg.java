package com.lin.controller.req;

import com.lin.common.rest.ReqMsg;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class AddCustomerGroupReqMsg extends ReqMsg {

    /**
     * 群名称
     */
    @Size(max = 100,message = "群名称太长了")
    @NotNull(message = "群名称不能为空")
    private String groupName;

    /**
     * 群简介
     */
    private String purpose;
    @NotNull(message = "客户ID不能为空")
    private Long customerId;

    @Size(min = 1,max = 10,message = "至少一位成员，最多10位成员")
    private List<Long> customerRelIds;
}
