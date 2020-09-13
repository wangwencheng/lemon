package org.lemon.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoginOSTypeEnum {
	ANDROID(1, "android"),
	IOS(2, "ios"),
	ANDROID_PAD(3, "android pad"),
	IPAD(4, "ipad"),
	PC(5, "pc"),
	;
	private Integer type;
	private String desc;

	public static LoginOSTypeEnum getEnum(int type) {
		LoginOSTypeEnum[] enums = LoginOSTypeEnum.values();
		for (LoginOSTypeEnum anEnum : enums) {
			if (anEnum.type == type) {
				return anEnum;
			}
		}
		return null;
	}
}
