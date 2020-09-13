package org.lemon.user.api.dto.request;

import lombok.Data;

@Data
public class SmsSendDTO {
	private Integer type; // 详解SmsTypeEnum枚举
	private String mobile;
}
