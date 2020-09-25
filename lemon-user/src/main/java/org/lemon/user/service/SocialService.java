package org.lemon.user.service;

import org.lemon.common.core.service.BaseService;
import org.lemon.user.api.entity.UserInfo;

/**
 * 社交登录服务接口
 * @author wwc
 */
public interface SocialService extends BaseService<UserInfo> {

	/**
	 * 通过手机号、第三方标识获取用户信息
	 * @param openId
	 * @return
	 */
	UserInfo getUserInfo(String openId);
}
