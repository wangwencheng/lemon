package org.lemon.user.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("注册消息体")
public class RegisterResponse {
	@ApiModelProperty("用户编号")
	private Long userNo;
	@ApiModelProperty("手机号")
	private String mobile;
}
