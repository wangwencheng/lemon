package org.lemon.common.security.component;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.core.constant.CommonConstant;
import org.lemon.common.core.constant.SecurityConstant;
import org.lemon.common.security.exception.LemonAuth2Exception;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 根据checktoken 的结果转化用户信息
 *
 * @author wwc
 */
@Slf4j
public class GbUserAuthenticationConverter implements UserAuthenticationConverter {
	private static final String N_A = "N/A";

	/**
	 * Extract information about the user to be used in an access token (i.e. for resource servers).
	 *
	 * @param authentication an authentication representing a user
	 * @return a map of key values representing the unique information about the user
	 */
	@Override
	public Map<String, ?> convertUserAuthentication(Authentication authentication) {
		Map<String, Object> response = new LinkedHashMap<>();
		response.put(USERNAME, authentication.getName());
		if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
			response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
		}
		return response;
	}

	/**
	 * Inverse of {@link #convertUserAuthentication(Authentication)}. Extracts an Authentication from a map.
	 *
	 * @param map a map of user information
	 * @return an Authentication representing the user or null if there is none
	 */
	@Override
	public Authentication extractAuthentication(Map<String, ?> map) {
		if (map.containsKey(USERNAME)) {
			validateTenantId(map);
			Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
			String username = (String) map.get(USERNAME);
			Long userNo = Long.valueOf(map.get(SecurityConstant.DETAILS_USER_NO).toString());
			String mobile = (String) map.get(SecurityConstant.DETAILS_MOBILE);
			Integer tenantId = (Integer) map.get(SecurityConstant.DETAILS_TENANT_ID);
			User user = new User(mobile, N_A, true
					, true, true, true, authorities);
			return new UsernamePasswordAuthenticationToken(user, N_A, authorities);
		}
		return null;
	}

	private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
		Object authorities = map.get(AUTHORITIES);
		if (authorities instanceof String) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
		}
		if (authorities instanceof Collection) {
			return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
					.collectionToCommaDelimitedString((Collection<?>) authorities));
		}
		throw new IllegalArgumentException("Authorities must be either a String or a Collection");
	}

	private void validateTenantId(Map<String, ?> map){
		String headerValue =  getCurrentTenantId();
		Integer userValue = (Integer) map.get(SecurityConstant.DETAILS_TENANT_ID);
		if(StrUtil.isNotBlank(headerValue) && !userValue.toString().equals(headerValue)){
			log.warn("请求头中的租户ID({})和用户的租户ID({})不一致",headerValue,userValue);
			throw new LemonAuth2Exception(SpringSecurityMessageSource.getAccessor().getMessage("AbstractUserDetailsAuthenticationProvider.badTenantId","Bad tenant ID"));
		}
	}

	private Optional<HttpServletRequest> getCurrentHttpRequest() {
		return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
				.filter(requestAttributes -> ServletRequestAttributes.class.isAssignableFrom(requestAttributes.getClass()))
				.map(requestAttributes -> ((ServletRequestAttributes) requestAttributes))
				.map(ServletRequestAttributes::getRequest);
	}

	private String getCurrentTenantId() {
		return getCurrentHttpRequest().map(httpServletRequest -> httpServletRequest.getHeader(CommonConstant.TENANT_ID)).orElse(null);
	}


}
