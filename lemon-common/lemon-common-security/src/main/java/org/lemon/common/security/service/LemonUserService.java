package org.lemon.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface LemonUserService {
	UserDetails info(String username) throws UsernameNotFoundException;

	UserDetails socialInfo(String openId) throws UsernameNotFoundException;
}
