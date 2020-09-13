package org.lemon.user.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.lemon.common.core.model.BaseEntity;

@Data
@Accessors(chain = true)
@TableName("user_login_info")
@ApiModel("用户登录信息")
public class UserLoginInfo extends BaseEntity<UserLoginInfo> {
	private static final long serialVersionUID = -6460395430301372606L;

	@TableId(value = "login_no", type = IdType.ID_WORKER)
	@ApiModelProperty("登录号")
	private Long loginNo;

	@TableField(value = "user_no", insertStrategy = FieldStrategy.NOT_NULL)
	@ApiModelProperty("用户编号")
	private Long userNo;

	@TableField(value = "login_os_type", insertStrategy = FieldStrategy.NOT_NULL)
	@ApiModelProperty("登录平台类型,具体参考LoginOSTypeEnum枚举：1-android 2-iOS 3-android pad 4-ipad 5-pc")
	private Integer loginOsType;

	@TableField(value = "device_id", insertStrategy = FieldStrategy.NOT_NULL)
	@ApiModelProperty("设备编号")
	private String deviceId;

	@TableField(value = "login_ip", insertStrategy = FieldStrategy.NOT_NULL)
	@ApiModelProperty("登陆IP")
	private String loginIp;

	@TableField(value = "device_factory", insertStrategy = FieldStrategy.NOT_NULL)
	@ApiModelProperty("设备厂商")
	private String deviceFactory;
}
