package org.lemon.common.security.component;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class LemonPasswordEncoder implements PasswordEncoder {
	private static final Integer LOG_ROUNDS = 12;
	private PasswordEncoder passwordEncoder;

	public LemonPasswordEncoder() {
		String encodingId = "bcrypt";
		Map<String, PasswordEncoder> encoders = new HashMap();
		encoders.put(encodingId, new BCryptPasswordEncoder());
		encoders.put("ldap", new LdapShaPasswordEncoder());
		encoders.put("MD4", new Md4PasswordEncoder());
		encoders.put("md5", new Md5PasswordEncoder());
		encoders.put("noop", NoOpPasswordEncoder.getInstance());
		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
		encoders.put("scrypt", new SCryptPasswordEncoder());
		encoders.put("SHA-1", new MessageDigestPasswordEncoder("SHA-1"));
		encoders.put("SHA-256", new MessageDigestPasswordEncoder("SHA-256"));
		encoders.put("sha256", new StandardPasswordEncoder());
		this.passwordEncoder = new DelegatingPasswordEncoder(encodingId, encoders);
	}

	public String encode(CharSequence rawPassword) {
		return encode(rawPassword, false);
	}

	public String encode(CharSequence rawPassword, boolean ignoreSign) {
		String encode = this.passwordEncoder.encode(rawPassword);
		return ignoreSign ? encode.replaceAll("\\{[^}]*\\}", "") : encode;
	}

	@Override
	public boolean matches(CharSequence inputPassword, String dbPassword) {
		return passwordEncoder.matches(inputPassword, dbPassword);
	}

	public String salt() {
		return BCrypt.gensalt(LOG_ROUNDS);
	}
}
