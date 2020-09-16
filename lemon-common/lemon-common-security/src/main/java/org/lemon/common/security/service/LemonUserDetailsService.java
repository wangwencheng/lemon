package org.lemon.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author wwc
 */
public interface LemonUserDetailsService extends UserDetailsService {

	/**
	 * 手机验证码登录/社交登录
	 * @param openId
	 * @param type
	 * @return
	 * @throws UsernameNotFoundException
	 */
	UserDetails loadUserBySocial(String openId, String type)  throws UsernameNotFoundException;
}
