package org.lemon.user.api.dto.request;

import lombok.Data;

@Data
public class BindDTO {
	private String mobile;
	private Integer bindType; // 详见BindTypeEnum
	private String code;
	private String oldIden;
	private String newIden;
	private Integer thirdType;
}
