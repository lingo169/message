package com.lin.controller.req;

import com.lin.common.rest.ReqMsg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@ApiModel(description = "登录信息")
@Getter
@Setter
public class LoginReqMsg extends ReqMsg {

    @ApiModelProperty(value = "账号", required = true)
    @NotBlank(message = "用户名不能为空")
    private String customerNo;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;
}
