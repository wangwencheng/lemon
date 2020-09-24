package org.lemon.user.service;

import org.lemon.user.api.dto.request.LoginDTO;

public interface UserLoginInfoService {
	void login(LoginDTO dto);
}
