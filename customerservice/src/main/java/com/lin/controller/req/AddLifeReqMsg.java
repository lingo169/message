package com.lin.controller.req;

import com.lin.common.rest.ReqMsg;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class AddLifeReqMsg extends ReqMsg {

    @NotNull(message = "客户号不能为空")
    private Long customerId;

    @ApiModelProperty(value = "标签")
    private Long lables;

    @ApiModelProperty(value = "权限")
    private String limitFlag;

    @NotBlank(message = "内容不能为空")
    private String contents;

    private List<String> fileUrl;

        private String province;

    private String city;

    private String county;
}
