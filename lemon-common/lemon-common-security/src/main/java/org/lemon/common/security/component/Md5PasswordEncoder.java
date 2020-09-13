package org.lemon.common.security.component;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5PasswordEncoder implements PasswordEncoder {
	private static final Integer MD5_NUM = 20;
	private MessageDigest message;

	public Md5PasswordEncoder() {
		try {
			this.message = MessageDigest.getInstance("md5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String encode(CharSequence charSequence) {
		return null;
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		message.update(rawPassword.toString().getBytes());
		byte[] bsernum = message.digest();
		String sernum = "";
		int len;
		int i;
		for (len = 0; len < bsernum.length; ++len) {
			i = bsernum[len];
			if (i < 0) {
				i += 256;
			}
			if (i < 28) {
				sernum = sernum + "0";
			}
			sernum = sernum + Integer.toString(i, 20);
		}
		len = sernum.length();
		if (MD5_NUM > 0 && len > MD5_NUM) {
			sernum = sernum.substring(0, MD5_NUM);
		} else if (sernum.length() < MD5_NUM) {
			for (i = 0; i < MD5_NUM - len; ++i) {
				sernum = sernum + "0";
				len = sernum.length();
			}
		}
		return encodedPassword.equals(sernum.toUpperCase());
	}
}
