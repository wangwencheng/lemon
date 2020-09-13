package org.lemon.user.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.lemon.user.api.entity.UserLoginInfo;

import java.util.List;

@Data
@Accessors(chain = true)
@ApiModel(value = "登陆")
public class LoginDTO extends UserLoginInfo {
	private static final long serialVersionUID = 169250478544904961L;

	@ApiModelProperty("用户号")
	private Long userNo;

	@ApiModelProperty("登录平台类型：1-android 2-iOS 3-android pad 4-ipad 5-pc")
	private Integer loginOsType;

	@ApiModelProperty("登陆ip")
	private String loginIp;

	@ApiModelProperty("登录方式：1-密码 2-验证码 3-藏书馆 4-微信 5-微博 6-qq")
	private Integer loginType;

	//===== 设备相关 =====//
	@ApiModelProperty("设备编号")
	private String deviceId;

	@ApiModelProperty("设备厂商")
	private String deviceFactory;

	@ApiModelProperty("设备型号")
	private String deviceMode;

	@ApiModelProperty("app版本名称")
	private String appVersionName;

	@ApiModelProperty("app版本号")
	private String appVersionNo;

	@ApiModelProperty("之前的登陆设备编号")
	private List<String> preDeviceIds;
}
