package org.lemon.common.gateway.constant;

/**
 * 网关常量
 * @author wwc
 */
public interface GatewayConstant {
	/**
	 * 网关上下文缓存KEY
	 */
	String CACHE_GATEWAY_CONTEXT = "cacheGatewayContext";
	/**
	 * 签名KEY
	 */
	String SIGN_KEY = "sign";
	/**
	 * 签名密钥KEY
	 */
	String SIGN_SECRET_KEY = "secret_key";
	/**
	 * 时间戳KEY
	 */
	String TIMESTAMP_KEY = "timestamp";
	/**
	 * 随机数KEY
	 */
	String NONCE_KEY = "nonce";
	/**
	 * 业务参数KEY
	 */
	String PARAMS_KEY = "params";
	/**
	 * 随机数缓存KEY
	 */
	String NONCE_CACHE_KEY = "api:nonce:";
	/**
	 * 响应代码KEY
	 */
	String RESPONSE_CODE_KEY = "code";

}
