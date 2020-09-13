package org.lemon.common.security.util;


import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全工具类
 * @author Donald
 */
@UtilityClass
public class SecurityUtil {
	/**
	 * 获取Authentication
	 */
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 *
	 * @param authentication
	 * @return GbUser
	 * <p>
	 */
	public GbUser getUser(Authentication authentication) {
		if(authentication != null){
			Object principal = authentication.getPrincipal();
			if (principal instanceof GbUser) {
				return (GbUser) principal;
			}
		}
		return null;
	}

	/**
	 * 获取用户
	 */
	public GbUser getUser() {
		Authentication authentication = getAuthentication();
		return getUser(authentication);
	}



}
