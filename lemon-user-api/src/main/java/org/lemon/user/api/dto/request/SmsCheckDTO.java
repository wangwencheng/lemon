package org.lemon.user.api.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

@Data
@Builder(toBuilder = true)
public class SmsCheckDTO {
	private String mobile;
	private Integer type; // 详解SmsTypeEnum枚举
	private String code;

	@Tolerate
	public SmsCheckDTO() {
	}
}
