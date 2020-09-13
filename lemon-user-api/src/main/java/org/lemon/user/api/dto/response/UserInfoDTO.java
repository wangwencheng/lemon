package org.lemon.user.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "用户消息体")
public class UserInfoDTO {
	@ApiModelProperty(value = "用户号", dataType = "Long")
	private Long userNo;
	@ApiModelProperty(value = "用户名", dataType = "String")
	private String username;
	@ApiModelProperty(value = "手机号", dataType = "String")
	private String mobile;
	@ApiModelProperty(value = "邮箱", dataType = "String")
	private String email;
	@ApiModelProperty(value = "昵称", dataType = "String")
	private String nickName;
	@ApiModelProperty(value = "真实姓名", dataType = "String")
	private String realName;
	@ApiModelProperty(value = "性别 1:男,2:女,0:未知", dataType = "Integer")
	private Integer sex;
	@ApiModelProperty(value = "头像", dataType = "String")
	private String avatar;
	@ApiModelProperty(value = "个性签名", dataType = "String")
	private String signature;
}
