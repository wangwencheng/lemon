package org.lemon.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字典类型
 *
 * @author Donald
 */
@Getter
@AllArgsConstructor
public enum DictTypeEnum {
	/**
	 * 字典类型-系统类（不可修改）
	 */
	SYSTEM("1", "系统类"),

	/**
	 * 字典类型-业务类
	 */
	BIZ("2", "业务类");

	/**
	 * 类型
	 */
	private String type;
	/**
	 * 描述
	 */
	private String description;
}
