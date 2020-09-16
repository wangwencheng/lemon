package org.lemon.common.security.util;


import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 * 安全工具类
 *
 * @author wwc
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
    public User getUser(Authentication authentication) {
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                return (User) principal;
            }
        }
        return null;
    }

    /**
     * 获取用户
     */
    public User getUser() {
        Authentication authentication = getAuthentication();
        return getUser(authentication);
    }


}
