package org.lemon.auth.controller;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Token模块
 *
 * @author Donald
 */
@Slf4j
@Controller
@RequestMapping("token")
public class TokenController {
    @Autowired
    private TokenStore tokenStore;

    @GetMapping("toTogin")
    public String login(HttpServletRequest request) {
        return "login";
    }

    @ResponseBody
    @RequestMapping("test")
    public String test() {
        return "success";
    }

    /**
     * 退出token
     *
     * @param authHeader Authorization
     */
    @DeleteMapping("logout")
    public R logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader, @RequestParam String from) {
        if (StrUtil.isBlank(authHeader)) {
            return R.ok(Boolean.FALSE, "退出失败，token 为空");
        }
        String tokenValue = authHeader.replace(OAuth2AccessToken.BEARER_TYPE, StrUtil.EMPTY).trim();
        return delToken(tokenValue, from);
    }

    /**
     * 令牌管理调用
     *
     * @param token token
     * @return
     */
    @ResponseBody
    @DeleteMapping("/{token}")
    public R<Boolean> delToken(@PathVariable("token") String token, @RequestParam String from) {
        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
        if (accessToken == null || StrUtil.isBlank(accessToken.getValue())) {
            return R.ok(Boolean.TRUE, "退出失败，token 无效");
        }

        OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(accessToken);

        // 清空access token
        tokenStore.removeAccessToken(accessToken);
        // 清空 refresh token
        OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
        tokenStore.removeRefreshToken(refreshToken);
        return R.ok();
    }
}
