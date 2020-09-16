package org.lemon.common.security.mobile;

import lombok.SneakyThrows;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 手机号登录令牌
 *
 * @author wwc
 */
public class MobileAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

	private final Object principal;
	private final String type;

	public MobileAuthenticationToken(String openId, String type) {
		super(null);
		this.principal = openId;
		this.type = type;
		setAuthenticated(false);
	}

	public MobileAuthenticationToken(Object principal, String from,
									 Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		this.type = from;
		super.setAuthenticated(true);
	}

	@Override
	public Object getPrincipal() {
		return this.principal;
	}

	public String getType() {
		return this.type;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	@SneakyThrows
	public void setAuthenticated(boolean isAuthenticated) {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}

		super.setAuthenticated(false);
	}

	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
	}
}

