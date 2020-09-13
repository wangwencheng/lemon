package org.lemon.common.core.constant;

/**
 * 缓存常量
 * @author Donald
 */
public interface CacheConstant {
	/**
	 * 全局缓存，在缓存名称上加上该前缀表示该缓存不区分租户，比如:
	 * <p/>
	 * {@code @Cacheable(value = CacheConstants.GLOBALLY+CacheConstants.MENU_DETAILS, key = "#roleId  + '_menu'", unless = "#result == null")}
	 */
	String GLOBALLY = "gl:";
	/**
	 * 短信验证码前缀
	 */
	String SMS_CODE_KEY = "sms_code_";
	/**
	 * 路由存放
	 */
	String ROUTE_KEY = "gateway_route_key";
	/**
	 *
	 */
	String TENANT_KEY = "tenant_key";
	/**
	 * redis reload 事件
	 */
	String ROUTE_REDIS_RELOAD_TOPIC = "gateway_redis_route_reload_topic";
	/**
	 * 内存reload 时间
	 */
	String ROUTE_JVM_RELOAD_TOPIC = "gateway_jvm_route_reload_topic";
	/**
	 * oauth 客户端信息
	 */
	String CLIENT_DETAILS_KEY = "elib_oauth:client:details";
	/**
	 * 用户信息缓存
	 */
	String USER_DETAILS = "user_details";
	/**
	 * 用户信息缓存
	 */
	String OPR_DETAILS = "opr_details";
	/**
	 * 字典信息缓存
	 */
	String DICT_DETAILS = "dict_details";
	/**
	 * 权限信息缓存
	 */
	String AUTH_DETAILS = "auth_details";
}
