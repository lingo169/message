package com.lin.common.error;

/**
 * 自定义异常.
 */
public class CustomRuntimeException extends Exception {

    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 8142675620713395172L;

    /**
     * 结果码.
     */
    private ErrorCode errorCode;

    /**
     * 信息.
     */
    private String message;

    /**
     * 构造方法.
     *
     * @param errorCode 错误码.
     * @param message   错误信息.
     */
    public CustomRuntimeException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * 自定义构造方法.
     *
     * @param errorCode 错误码.
     * @param message   错误信息.
     * @param cause     异常.
     */
    public CustomRuntimeException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * 返回错误码.
     *
     * @return 返回错误码.
     */
    public ErrorCode getErrorCode() {
        return errorCode;
    }

    /**
     * 设置错误码.
     * @param errorCode 错误码
     */
    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 获取错误信息.
     * @return 错误信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 错误信息设置.
     * @param message 错误信息.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
