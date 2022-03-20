package com.lin.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class ReqMsg implements Serializable {

    @ApiModelProperty(value = "分页页数")
    private Integer pageNo;

    @ApiModelProperty(value = "分页页码")
    private Integer pageSize;

    @ApiModelProperty(value = "随机数", required = true)
    @NotBlank(message = "随机数不能为空")
    private String random;

    @ApiModelProperty(value = "时间戳", required = true)
    @NotBlank(message = "时间戳不能为空")
    private String timestamp;
}
