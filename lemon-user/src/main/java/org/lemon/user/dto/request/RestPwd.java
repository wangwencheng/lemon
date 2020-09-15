package org.lemon.user.dto.request;

import lombok.Data;

@Data
public class RestPwd {
	private String code;
	private String mobile;
	private String newPassword;
}
