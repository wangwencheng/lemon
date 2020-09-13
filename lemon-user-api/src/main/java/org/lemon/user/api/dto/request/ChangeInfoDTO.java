package org.lemon.user.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ChangeInfoDTO {
	private String username;
	private String email;
	private String nickName;
	private String realName;
	private Integer sex;
	private String avatar;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date birthday;
	private String signature;
}
