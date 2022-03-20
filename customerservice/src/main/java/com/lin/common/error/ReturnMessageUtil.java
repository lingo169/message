package com.lin.common.error;

import com.lin.controller.res.ResMsg;

public class ReturnMessageUtil {
	/**
	 * 无异常 请求成功并有具体内容返回
	 * @param object
	 * @return
	 */
	public static ResMsg<Object> sucess(Object object) {
		ResMsg<Object> message = new ResMsg<>().withCode(ErrorCode.SUCCESS).withMessage("成功").withData(object);
		return message;
	}
	/**
	 * 无异常 请求成功并无具体内容返回
	 * @return
	 */
	public static ResMsg<Object> sucess() {
		ResMsg<Object> message = new ResMsg<>().withCode(ErrorCode.SUCCESS).withMessage("成功");
		return message;
	}
	/**
	 * 有自定义错误异常信息
	 * @param msg
	 * @return
	 */
	public static ResMsg<Object> error(ErrorCode errorCode, String msg) {
		ResMsg<Object> message = new ResMsg<>().withCode(errorCode).withMessage(msg);
		return message;
	}
}
