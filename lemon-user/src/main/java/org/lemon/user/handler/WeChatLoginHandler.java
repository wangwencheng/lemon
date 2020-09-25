package org.lemon.user.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lemon.user.api.entity.UserInfo;
import org.springframework.stereotype.Component;


/**
 * 微信登录
 * @author wwc
 */
@Slf4j
@Component("WX")
@AllArgsConstructor
public class WeChatLoginHandler extends AbstractLoginHandler {

	/**
	 * 通过code调用微信获取唯一标识
	 *
	 * @param code
	 * @return
	 */
	@Override
	public String identify(String code) {
		return null;
	}

	/**
	 * 通过openId获取用户信息
	 *
	 * @param openId
	 * @return
	 */
	@Override
	public UserInfo info(String openId) {
		return null;
	}
}
