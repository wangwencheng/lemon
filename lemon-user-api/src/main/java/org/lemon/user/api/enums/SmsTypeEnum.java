package org.lemon.user.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SmsTypeEnum {
	MOBILE_REGISTER(1, "mobile_add", "Register", "手机注册"),
	MOBILE_BIND(2, "mobile_bind", "Bind", "手机绑定"),
	RE_PASSWORD(3, "reset_pwd", "Retrieve", "重置密码"),
	;
	private Integer type;
	private String code;
	private String templateType;
	private String desc;

	public static final SmsTypeEnum getEnum(Integer type) {
		if (null == type) {
			return null;
		}
		SmsTypeEnum[] enums = SmsTypeEnum.values();
		for (SmsTypeEnum anEnum : enums) {
			if (anEnum.type == type) {
				return anEnum;
			}
		}
		return null;
	}
}
