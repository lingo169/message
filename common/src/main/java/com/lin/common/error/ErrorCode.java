package com.lin.common.error;

/**
 * 异常常量类.
 *
 * @author lingo
 * @date: 2019-02-27
 * @version: V1.0.0
 */
public enum ErrorCode {
    /**
     * 成功.
     */
    SUCCESS("000000", "成功"),

    ILLEGAL_PARAM("999991","非法参数"),
    /**
     * 未知错误.
     */
    UN_KNOW_ERROR("999999", "未知错误"),


    EMAIL_OR_MOBILE_NOT_NULL("100001","邮箱或者手机号不能为空"),

    MOBILE_FORMAT_ERROR("100002","手机格式错误"),

    CUSTOMER_EXISTS("100003","账号已经存在"),

    CUSTOMER_NOT_EXISTS("100004","账号不存在"),
    PASSWORD_NOT_EQUALLY("100005","密码与确认密码不一致"),
    RESEND_MODIFYPASS_EAMIL("100006","修改密码超时，请重新发起密码重置邮件"),
    ILLEGAL_MODIFYPASS_TOKEN("100007","非法修改密码认证码，请重新发起密码重置邮件"),
    RELATIONSHIP_NOT_EXISTS("100008","好友关系不存在，请先添加好友"),
    RELATIONSHIP_INIT("100009","已添加好友，等待对方确认"),
    RELATIONSHIP_EXISTS("100010","好友关系已存在"),
    RELATIONSHIP_REFUSE("100011","对方拒绝您添加好友"),
    GROUP_NOT_EXISTS("100012","群组不存在"),
    /**
     * 活动不存在.
     */
    UNAUTHORIZED_ERROR("401", "授权验证不通过"),
    /**
     * 不支持渠道.
     */
    UN_SUPPORT_CHANNEL("2013", "不支持渠道"),
    /**
     * 获取不到HEADER 的 TOKEN
     */
    HEADER_TOKEN("2014", "请求头 TOKEN未知错误"),
    /**
     * 参数校验不通过.
     */
    ERR_CODE_INVALIDATION("000001", "参数校验不通过"),

    /**
     * 重放攻击.
     */
    REPLAY_ATTACK_ERROR("000002", "重放攻击,请正确填写参数"),
    /**
     * 请求内容太大.
     */
    BIG_LONG("000003", "请求内容太大"),
    /**
     * 签名错误
     */
    SIGN_ERROR("000004","签名错误"),
    /**
     * 登录错误.
     */
    LOGIN_ERROR("000010", "用户名或者密码错误"),
    LOGIN_NOT_EXISTS("000011", "账户不存在，请前往注册"),
    /**
     * 序列号参数过短，不能少于22位.
     */
    SERIALIZABLE_LENGTH_LESS("100010", "序列号必须19位"),
    /**
     * 序列号参数过短，不能少于22位.
     */
    SERIALIZABLE_LENGTH_THAN("100011", "序列号参数过长，不能大于36位"),

    ACCESS_TOKEN_NULL("100031", "获取不到ACCESS_TOKEN"),

    /**
     *
     */
    CONVERT_ERROR("100032","参数转换错误"),
    /**
     * 参数不能为空.
     * MAP_NON_EXISTENT("100021","初始化微信请求不能为空");
     */
    MAP_NON_EXISTENT("100021", "初始化微信请求不能为空"),
    /**
     * 参数不能为空.
     * MAP_NON_EXISTENT("100021","初始化微信请求不能为空");
     */
    PAY_FAIL("100051", "支付失败，请联系客服"),
    /**
     * 退款失败
     */
    REFUND_PAY_FAIL("100052", "退款失败，请联系客服"),
    /**
     *
     */
    ORI_ORDER_NOT_EXISTS("100054", "找不到原订单或者订单支付未成功，请联系客服"),
    /**
     * 商户不存在.
     */
    MERC_NON_EXISTENT("100012", "商户不存在或者状态异常"),
    /**
     * 订单不存在
     */
    ORDER_NOT_EXISTS("100013","订单不存在"),

    ADD_SHOPCART_EXISTS("100014","该商品已经添加到购物车")
    ;
    /**
     * 错误码.
     */
    private String code;
    /**
     * 错误信息.
     */
    private String message;

    /**
     * 构造器.
     *
     * @param code    错误码
     * @param message 错误信息
     */
    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
