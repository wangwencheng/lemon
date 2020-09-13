package org.lemon.user.api.dto.request;

import lombok.Data;

@Data
public class ChangePwdDTO {
	private String oldPassword;
	private String newPassword;
}
