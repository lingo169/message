package com.lin.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "登录返回信息")
@Getter
@Setter
public class LoginResMsg {

    @ApiModelProperty(value = "sessionid&token", required = true)
    private String sessionid;

    @ApiModelProperty(value = "账号", required = true)
    private String customerNo;

    private String namespace;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column cs_customer.ROOM
     *
     * @mbg.generated Wed Dec 22 10:16:36 CST 2021
     */
    private String room;

    @ApiModelProperty(value = "头像")
    private String portrait;



    @ApiModelProperty(value = "用户等级", required = true)
    private String levelNo;

    @ApiModelProperty(value = "昵称", required = true)
    private String customerName;

    @ApiModelProperty(value = "性别,male：男性,female：女性", required = true)
    private String gender;


    @ApiModelProperty(value = "证件类型")
    private String cardType;

    @ApiModelProperty(value = "真实名称")
    private String cardName;

    @ApiModelProperty(value = "证件号（身份证号）")
    private String cardId;

    @ApiModelProperty(value = "手机",required = true)
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "用户状态")
    private String status;
}