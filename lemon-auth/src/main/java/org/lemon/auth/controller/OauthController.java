package org.lemon.auth.controller;

import org.lemon.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * @author wwc
 */
@RestController
@RequestMapping("/oauth")
public class OauthController {
    @Autowired
    private TokenEndpoint tokenEndpoint;

    @RequestMapping(value = "/token", method = {RequestMethod.GET, RequestMethod.POST})
    public R accessToken(Principal principal, @RequestParam Map<String, String> parameters, HttpServletRequest request) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        return R.ok(oAuth2AccessToken);
    }
}
