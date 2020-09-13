package org.lemon.auth.controller;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.core.util.R;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Token模块
 *
 * @author Donald
 */
@Slf4j
@RestController
@RequestMapping("/token")
public class TokenController {

	@GetMapping("/login")
	public R login(HttpServletRequest request) {
		return R.failed("不支持的登录方式");
	}

	/**
	 * 退出token
	 *
	 * @param authHeader Authorization
	 */
	@DeleteMapping("/logout")
	public R logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader, @RequestParam String from) {
		if (StrUtil.isBlank(authHeader)) {
			return R.failed("退出失败，token 为空");
		}
		String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, StrUtil.EMPTY).trim();
		return R.failed("退出失败");
	}
}
