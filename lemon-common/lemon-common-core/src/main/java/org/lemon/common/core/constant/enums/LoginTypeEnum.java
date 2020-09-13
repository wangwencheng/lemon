package org.lemon.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginTypeEnum {
	/**
	 * 账号密码登录
	 */
	PWD("PWD", "账号密码登录"),

	/**
	 * 验证码登录
	 */
	SMS("SMS", "验证码登录"),

	/**
	 * 藏书馆登录
	 */
	CSG("CSG", "藏书馆登录"),

	/**
	 * QQ登录
	 */
	QQ("QQ", "QQ登录"),

	/**
	 * 微信登录
	 */
	WECHAT("WX", "微信登录"),

	/**
	 * 微博登录
	 */
	WEIBO("WB", "微博登录");
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 描述
	 */
	private String description;
}
