package org.lemon.common.gateway.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 签名类型
 * @author wwc
 */
@Getter
@AllArgsConstructor
public enum SignTypeEnum {
	MD5("MD5","md5签名"),
	SHA256("SHA256","sha256签名");
	/**
	 * 签名类型
	 */
	private String type;
	/**
	 * 描述
	 */
	private String desc;

	public static SignTypeEnum fromType(String type) {
		switch(type) {
			case "MD5":
				return MD5;
			case "SHA256":
				return SHA256;
			default:
				return null;
		}
	}
}
