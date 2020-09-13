package org.lemon.user.api.dto.request;

import lombok.Data;

@Data
public class RestPwdDTO {
	private String code;
	private String mobile;
	private String newPassword;
}
