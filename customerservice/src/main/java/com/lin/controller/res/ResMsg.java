package com.lin.controller.res;

import com.lin.common.error.ErrorCode;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ResMsg<T> implements Serializable {
    /**
     * 结果码
     */
    @ApiModelProperty(value = "错误码", required = true)
    private ErrorCode errorCode=ErrorCode.SUCCESS;
    /**
     * 数据
     */
    @ApiModelProperty(value = "具体返回值", required = true)
    private T data;
    /**
     * 信息
     */
    @ApiModelProperty(value = "错误信息", required = true)
    private String message;

    /**
     * 返回结果码
     *
     * @return 结果码
     */
    public String getCode() {
        return this.errorCode.getCode();
    }

    /**
     * 返回描述
     *
     * @return 返回描述
     */
    public String getMessage() {
        if (message != null) {
            return this.message;
        }
        return this.errorCode.getMessage();
    }

    public T getData() {
        return data;
    }

    /**
     * 设置结果编码
     *
     * @param code 结果编码
     * @return Builder
     */
    public ResMsg<T> withCode(ErrorCode code) {
        this.errorCode = code;
        return this;
    }

    /**
     * 设置数据
     *
     * @param data 数据
     * @return this
     */
    public ResMsg<T> withData(T data) {
        this.data = data;
        return this;
    }

    /**
     * 设置信息
     *
     * @param message 信息
     * @return this
     */
    public ResMsg<T> withMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * 返回成功码的构建器
     *
     * @return 成功码的构建器
     */
    public static ResMsg<?> ok() {
        return ok(null);
    }

    /**
     * 返回成功码的构建器
     *
     * @param data 响应体
     * @param <T>  响应体类型
     * @return 成功码的构建器
     */
    public static <T> ResMsg<T> ok(T data) {
        return new ResMsg().withCode(ErrorCode.SUCCESS).withData(data);
    }

    /**
     * 返回指定编码的构建器
     *
     * @param code 编码
     * @return 成功码的构建器
     */
    public static ResMsg<?> code(ErrorCode code) {
        return new ResMsg().withCode(code);
    }
}
