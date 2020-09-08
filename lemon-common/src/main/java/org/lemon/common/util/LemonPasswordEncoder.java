package org.lemon.common.util;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author ErvinWang
 */
public class LemonPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return null;
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return false;
    }
}
