package org.lemon.user.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SexEnum {
	FEMALE(0, "女"),
	MALE(1, "男"),
	LADYBOY(2, "未知"),
	;
	private Integer sex;
	private String desc;

	public static SexEnum getEnum(Integer sex) {
		if (null != sex) {
			if (sex == MALE.getSex()) {
				return MALE;
			}
			if (sex == FEMALE.getSex()) {
				return FEMALE;
			}
		}
		return LADYBOY;
	}
}
