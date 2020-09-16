package org.lemon.common.security.mobile;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.lemon.common.core.constant.SecurityConstant;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * 手机号登录验证filter
 * @author wwc
 */
public class MobileAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	@Getter
	@Setter
	private String openIdParameter = "openId";
	@Getter
	@Setter
	private String typeParameter = "type";
	@Getter
	@Setter
	private boolean postOnly = true;
	@Getter
	@Setter
	private AuthenticationEventPublisher eventPublisher;
	@Getter
	@Setter
	private AuthenticationEntryPoint authenticationEntryPoint;


	public MobileAuthenticationFilter() {
		super(new AntPathRequestMatcher(SecurityConstant.MOBILE_TOKEN_URL, "POST"));
	}

	@Override
	@SneakyThrows
	public Authentication attemptAuthentication(HttpServletRequest request,
												HttpServletResponse response) {
		if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}

		String openId = request.getParameter(openIdParameter);
		String type = request.getParameter(typeParameter);
		openId = Optional.ofNullable(openId).orElse("").trim();
		type = Optional.ofNullable(type).orElse("").trim();


		MobileAuthenticationToken mobileAuthenticationToken = new MobileAuthenticationToken(openId, type);

		setDetails(request, mobileAuthenticationToken);

		Authentication authResult = null;
		try {
			authResult = this.getAuthenticationManager().authenticate(mobileAuthenticationToken);

			logger.debug("Authentication success: " + authResult);
			SecurityContextHolder.getContext().setAuthentication(authResult);

		} catch (Exception failed) {
			SecurityContextHolder.clearContext();
			logger.debug("Authentication request failed: " + failed);

			eventPublisher.publishAuthenticationFailure(new BadCredentialsException(failed.getMessage(), failed),
					new PreAuthenticatedAuthenticationToken("access-token", "N/A"));

			try {
				authenticationEntryPoint.commence(request, response,
						new UsernameNotFoundException(failed.getMessage(), failed));
			} catch (Exception e) {
				logger.error("authenticationEntryPoint handle error:{}", failed);
			}
		}

		return authResult;
	}

	private void setDetails(HttpServletRequest request,
							MobileAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}
}

