package org.lemon.common.gateway.constant;

/**
 * 错误消息常量
 * @author Donald
 */
public interface ErrorMsgConstant {

	String SIGN_MISSING_SIGNATURE = "签名验证失败:SIGN不能为空";
	String SIGN_ILLEGAL_TYPE ="签名验证失败:签名方式非法";
	String SIGN_INVALID_SIGNATURE = "签名验证失败:无效签名";
	String ATTACK_MISSING_TIMESTAMP = "防重放攻击:TIMESTAMP不能为空";
	String ATTACK_INVALID_TIMESTAMP = "防重放攻击:请求已过期";
	String ATTACK_MISSING_NONCE = "防重放攻击:NONCE不能为空";
	String ATTACK_INVALID_NONCE = "防重放攻击:请求仅一次有效";
	String DECRYPT_ILLEGAL_PASSWORD = "解密失败:密码非法";
}
