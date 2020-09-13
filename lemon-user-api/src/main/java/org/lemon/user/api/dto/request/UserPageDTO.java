package org.lemon.user.api.dto.request;

import lombok.Data;
import org.lemon.user.api.entity.UserInfo;

@Data
public class UserPageDTO extends UserInfo {
	private String startCreateTime;
	private String endCreateTime;
}
