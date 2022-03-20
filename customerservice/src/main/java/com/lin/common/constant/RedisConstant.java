package com.lin.common.constant;

public class RedisConstant {

	private RedisConstant(){}
	/**
	 * 重放攻击的REDIS key.
	 */
	public static final String REPLAY_ATTACK_RANDOM="wdp:backstage:random";
	/**
	 * 登录token.
	 */
	public static final String WDP_TOKEN="wdp:backstage:token:";

	public static final String FORGET_PASSWORD = "cs:forget:password:token:";

	public static final String CUSTOMER_RELATIONSHIP = "cs:customer:relationship:";
	
}
