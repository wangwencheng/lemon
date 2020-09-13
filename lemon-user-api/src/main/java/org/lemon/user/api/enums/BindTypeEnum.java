package org.lemon.user.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BindTypeEnum {
	UN_BIND(0, "解绑"),
	BIND(1, "绑定"),
	RE_BIND(2, "重新绑定"),
	RE_ORDER_BIND(3, "有订单状态重新绑定") // 待定
	;
	private Integer type;
	private String desc;

	public static BindTypeEnum getEnum(Integer type) {
		if (null == type) {
			return null;
		}
		BindTypeEnum[] enums = BindTypeEnum.values();
		for (BindTypeEnum anEnum : enums) {
			if (anEnum.type == type) {
				return anEnum;
			}
		}
		return null;
	}
}
