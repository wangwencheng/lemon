package org.lemon.user.api.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
	private String mobile;
	private String password;
	private String code;
}
