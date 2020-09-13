package org.lemon.common.core.constant;

/**
 * 公共常量
 *
 * @author Donald
 */
public interface CommonConstant {
	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";
	/**
	 * header 中版本信息
	 */
	String VERSION = "VERSION";
	/**
	 * 请求头中的租户ID
	 */
	String TENANT_ID = "Tenant-Id";
	/**
	 * 请求头日志链路追踪id
	 */
	String HEADER_TRACE_ID = "x-traceId-header";
	/**
	 * 日志链路追踪ID
	 */
	String LOG_TRACE_ID = "traceId";
	/**
	 * 正常
	 */
	String STATUS_NORMAL = "1";
	/**
	 * 禁用
	 */
	String STATUS_DISABLE = "2";
	/**
	 * 开发环境
	 */
	String PROFILE_DEV = "dev";
	/**
	 * 测试环境
	 */
	String PROFILE_TEST = "test";
	/**
	 * 预发布环境
	 */
	String PROFILE_PRE = "pre";
	/**
	 * 生产环境
	 */
	String PROFILE_PROD = "prod";

	/**
	 * 正常日志类型
	 */
	Integer LOG_NORMAL_TYPE = 1;
}
