package com.lin.common.constant;

public class RedisConstant {

	private RedisConstant(){}
	/**
	 * 重放攻击的REDIS key.
	 */
	public static final String REPLAY_ATTACK_RANDOM="com:lin:random:";
	/**
	 * 登录token.
	 */
	public static final String WDP_TOKEN="com:lin:token:";

	public static final String FORGET_PASSWORD = "cs:forget:password:token:";

	/**
	 * 好友关系
	 */
	public static final String CUSTOMER_RELATIONSHIP = "cs:customer:relationship:";

	public static final String CUSTOMER_SOCKETIOCLIENT = "cs:customer:socketioclient:";

	
}
