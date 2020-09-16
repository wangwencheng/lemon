package org.lemon.common.security.component;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wwc
 */
public class GbAuthenticationKeyGenerator extends DefaultAuthenticationKeyGenerator {
	private static final String SCOPE = "scope";
	private static final String CLIENT_ID = "client_id";
	private static final String USERNAME = "username";

	@Override
	public String extractKey(OAuth2Authentication authentication) {
		Map<String, String> values = new LinkedHashMap();
		OAuth2Request authorizationRequest = authentication.getOAuth2Request();
		if (!authentication.isClientOnly()) {
			values.put(USERNAME, authentication.getName());
		}

		values.put(CLIENT_ID, authorizationRequest.getClientId());
		return generateKey(values);
	}

	public String extractKey(Authentication authentication, String clientId) {
		Map<String, String> values = new LinkedHashMap();
		if (authentication != null) {
			values.put(USERNAME, authentication.getName());
			values.put(CLIENT_ID, clientId);
		}
		return generateKey(values);
	}
}
