package org.lemon.user.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatusEnum {
	NORMAL(1, "正常"),
	FORBIDDEN(2, "禁用"),
	;
	private Integer status;
	private String desc;
}
