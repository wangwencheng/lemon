package org.lemon.user.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author wwc
 */
@Data
@TableName("user_info")
public class UserInfo {
	private static final long serialVersionUID = -6460395430301372602L;
	/**
	 * 用户号
	 */
	@TableId(value = "user_id", type = IdType.INPUT)
	private Long userId;
	/**
	 * 用户名
	 */
	@TableField(value = "username", insertStrategy = FieldStrategy.NOT_NULL, whereStrategy = FieldStrategy.NOT_EMPTY)
	private String username;
	/**
	 * 手机号
	 */
	@TableField(value = "mobile", insertStrategy = FieldStrategy.NOT_NULL, whereStrategy = FieldStrategy.NOT_EMPTY)
	private String mobile;
	/**
	 * 密码
	 */
	@TableField(value = "password", insertStrategy = FieldStrategy.NOT_NULL)
	private String password;
	/**
	 * 随机盐
	 */
	@TableField(value = "salt", insertStrategy = FieldStrategy.NOT_NULL)
	private String salt;
	/**
	 * 用户状态：1-正常 2-禁用
	 */
	@TableField(value = "user_status", insertStrategy = FieldStrategy.NOT_NULL, whereStrategy = FieldStrategy.NOT_EMPTY)
	private Integer userStatus;
//	/**
//	 * 邮箱
//	 */
//	@TableField(value = "email", insertStrategy = FieldStrategy.NOT_NULL)
//	private String email;
	/**
	 * 昵称
	 */
	@TableField(value = "nick_name", insertStrategy = FieldStrategy.NOT_NULL, whereStrategy = FieldStrategy.NOT_EMPTY)
	private String nickName;
	/**
	 * 真实姓名
	 */
	@TableField(value = "real_name", insertStrategy = FieldStrategy.NOT_NULL)
	private String realName;
	/**
	 * 性别：0-未知 1-男 2-女
	 */
	@TableField(value = "sex", insertStrategy = FieldStrategy.NOT_NULL, whereStrategy = FieldStrategy.NOT_EMPTY)
	private Integer sex;
	/**
	 * 头像
	 */
	@TableField(value = "avatar", insertStrategy = FieldStrategy.NOT_NULL)
	private String avatar;
//	/**
//	 * 生日
//	 */
//	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
//	@TableField(value = "birthday", insertStrategy = FieldStrategy.NOT_NULL)
//	private Date birthday;
}
