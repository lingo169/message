package com.lin.controller.req;

import com.lin.common.rest.ReqMsg;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class GroupAddMemberReqMsg extends ReqMsg {
    @NotNull(message = "群组ID不能为空")
    private Long customerGroupId;

    @Size(min = 1,max = 10,message = "至少一位成员，最多10位成员")
    private List<Long> customerRelIds;
}
