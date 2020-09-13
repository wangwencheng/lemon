package org.lemon.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统类型
 *
 * @author Donald
 */

@AllArgsConstructor
@Getter
public enum SystemTypeEnum {

	/**
	 * app应用
	 */
	APP("APP","app应用"),
	/**
	 * 运营管理系统
	 */
	OMS("OMS", "运营管理系统"),
	/**
	 * APP应用PC端
	 */
	PC("PC", "app应用PC端"),
	;

	/**
	 * 类型
	 */
	private String type;
	/**
	 * 描述
	 */
	private String description;

}
