package org.lemon.user.handler;

import org.lemon.user.api.entity.UserInfo;

/**
 * 抽象登录处理器
 * @author wwc
 */
public abstract class AbstractLoginHandler implements LoginHandler {

	/**
	 * 数据合法性校验
	 * @param loginStr 登录参数
	 * @return 默认不校验
	 */
	@Override
	public Boolean check(String loginStr) {
		return true;
	}

	/**
	 * 处理方法
	 *
	 * @param loginStr 登录参数
	 * @return
	 */
	@Override
	public UserInfo handle(String loginStr) {
		if (!check(loginStr)) {
			return null;
		}
		String identify = identify(loginStr);
		return info(identify);
	}
}
