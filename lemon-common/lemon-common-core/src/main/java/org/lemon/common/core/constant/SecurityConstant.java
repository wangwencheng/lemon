package org.lemon.common.core.constant;

/**
 * 安全常量
 * @author wwc
 */
public interface SecurityConstant {
	/**
	 * 前缀
	 */
	String LEMON_PREFIX = "lemon_";
	/**
	 * oauth 相关前缀
	 */
	String OAUTH_PREFIX = "oauth:";
	/**
	 * 只允许单设备登录客户端
	 */
	String DEVICE_ONLY_CLIENT = "app";
	/**
	 * 登录平台类型：1-android 2-iOS 3-android pad 4-ipad 5-pc
	 */
	String LOGIN_OS_TYPE_PARAMETER = "loginOsType";
	/**
	 * 设备号参数
	 */
	String DEVICE_ID_PARAMETER = "deviceId";
	/**
	 * 设备厂商
	 */
	String DEVICE_FACTORY_PARAMETER = "deviceFactory";
	/**
	 * 设备型号
	 */
	String DEVICE_MODE_PARAMETER = "deviceMode";
	/**
	 * app版本号
	 */
	String APP_VERSION_NO_PARAMETER = "appVersionNo";
	/**
	 * app版本名称
	 */
	String APP_VERSION_NAME_PARAMETER = "AppVersionName";
	/**
	 * 登录方式：1-密码 2-验证码 3-藏书馆 4-微信 5-微博 6-qq
	 */
	String LOGIN_TYPE_PARAMETER = "loginType";
	/**
	 * 角色前缀
	 */
	String ROLE = "ROLE_";
	/**
	 * OAUTH URL
	 */
	String OAUTH_TOKEN_URL = "/oauth/token";
	/**
	 * 验证码有效期
	 */
	int CODE_TIME = 900;
	/**
	 * 验证码间隔
	 */
	int CODE_INTERVAL = 60;
	/**
	 * 验证码间隔名称
	 */
	String CODE_INTERVAL_NAME = "interval";
	/**
	 * 验证码长度
	 */
	String CODE_SIZE = "4";
	/**
	 * 内部
	 */
	String FROM_IN = "Y";
	/**
	 * 标志
	 */
	String FROM = "from";
	/**
	 * 手机号登录URL
	 */
	String SMS_TOKEN_URL = "/mobile/token/sms";
	/**
	 * 社交登录URL
	 */
	String SOCIAL_TOKEN_URL = "/mobile/token/social";
	/**
	 * 自定义登录URL
	 */
	String MOBILE_TOKEN_URL = "/mobile/token/*";
	/**
	 * 刷新
	 */
	String REFRESH_TOKEN = "refresh_token";
	/**
	 * {bcrypt} 加密的特征码
	 */
	String BCRYPT = "{bcrypt}";
	/**
	 * {md5} 加密的特征码
	 */
	String MD5 = "{md5}";
	/**
	 * sys_oauth_client_details 表的字段，不包括client_id、client_secret
	 */
	String CLIENT_FIELDS = "client_id, CONCAT('{noop}',client_secret) as client_secret, resource_ids, scope, "
			+ "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
			+ "refresh_token_validity, additional_information, autoapprove";

	/**
	 * JdbcClientDetailsService 查询语句
	 */
	String BASE_FIND_STATEMENT = "select " + CLIENT_FIELDS
			+ " from oauth_client_details";

	/**
	 * 默认的查询语句
	 */
	String DEFAULT_FIND_STATEMENT = BASE_FIND_STATEMENT + " order by client_id";

	/**
	 * 按条件client_id 查询
	 */
	String DEFAULT_SELECT_STATEMENT = BASE_FIND_STATEMENT + " where client_id = ?";

	/**
	 * 客户端模式
	 */
	String CLIENT_CREDENTIALS = "client_credentials";

	/**
	 * 资源服务器默认bean名称
	 */
	String RESOURCE_SERVER_CONFIGURER = "resourceServerConfigurerAdapter";
	/**
	 * 用户号字段
	 */
	String DETAILS_USER_NO = "user_no";
	/**
	 * 用户名字段
	 */
	String DETAILS_USERNAME = "username";
	/**
	 * 手机号字段
	 */
	String DETAILS_MOBILE = "mobile";
	/**
	 * 租户ID字段
	 */
	String DETAILS_TENANT_ID = "tenant_id";
	/**
	 * 协议字段
	 */
	String DETAILS_LICENSE = "license";
	/**
	 * 项目的license
	 */
	String LEMON_LICENSE = "made by lemon";
	/**
	 * 激活字段 兼容外围系统接入
	 */
	String ACTIVE = "active";
}
