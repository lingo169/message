package com.lin.common.error;

/**
 * 异常话术类
 *
 * @author 王晨阳
 * @date: 2019-05-29
 * @version: V1.0.0
 */
public class ErrorMessage {
	public static final String LOGIN_ERROR="登录异常";
    /**
     * 活动不存在
     */
    public static final String ACTIVITY_NOT_EXIST = "活动不存在";
    /**
     * 有场次不在活动范围内
     */
    public static final String SCENE_NOT_EXIST = "有场次不在活动范围内";
    /**
     * 有场次不在活动范围内
     */
    public static final String DELETE_PUSH_MESSAGE_ERROR = "推送服务删除推送消息异常";
    /**
     * 不能编辑已经过期的以及正在进行的场次
     */
    public static final String NO_UPDATE_CYCLE_SCENE = "不能编辑已经过期的以及正在进行的场次";
    
    /**
     * 重放攻击一次
     */
    public static final String REPLAY_ATTACK_ERROR = "重放攻击";
    /**
     * 授权验证不通过
     */
    public static final String UNAUTHORIZED_ERROR = "授权验证不通过";
    
    public static final String BIG_LONG_ERROR = "请求内容过大";
}
