package com.lin.controller.req;

import com.lin.common.rest.ReqMsg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@ApiModel(description = "注册信息")
@Getter
@Setter
public class CustomerReqMsg extends ReqMsg {

    @ApiModelProperty(value = "账号", required = true)
    @NotBlank(message = "账号不能为空")
    private String customerNo;


    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String portrait;

    @ApiModelProperty(value = "登录密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;


    @ApiModelProperty(value = "昵称")
    private String customerName;

    /**
     * 0：女
     * 1：男
     */
    @ApiModelProperty(value = "性别")
    @NotBlank(message = "性别不能为空")
    private String gender;


    @ApiModelProperty(value = "手机号",required = true)
    @NotBlank(message = "手机号码不能为空")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;
}
