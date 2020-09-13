package org.lemon.common.core.constant.enums;

import org.lemon.common.core.util.IErrorCode;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 系统返回码
 *
 * @author Donald
 */
@AllArgsConstructor
@Getter
@ApiModel(description = "系统返回码")
public enum ErrorCodeEnum implements IErrorCode {
	/**
	 * 成功
	 */
	SUCCESS(0, "操作成功"),
	/**
	 * 失败
	 */
	FAIL(-1, "系统繁忙，请稍后再试"),

	//------------------------------通用错误码------------------------------//
	/**
	 * 缺少必要的请求参数
	 */
	PARAM_MISS(10000, "缺少必要的请求参数"),
	/**
	 * 请求参数类型错误
	 */
	PARAM_TYPE_ERROR(10001, "请求参数类型错误"),
	/**
	 * 请求参数绑定错误
	 */
	PARAM_BIND_ERROR(10002, "请求参数绑定错误"),
	/**
	 * 参数校验失败
	 */
	PARAM_VALID_ERROR(10003, "参数校验失败"),
	/**
	 * 404 没找到请求
	 */
	NOT_FOUND(10004, "404 没找到请求"),
	/**
	 * 消息不能读取
	 */
	MSG_NOT_READABLE(10005, "消息不能读取"),
	/**
	 * 不支持当前请求方法
	 */
	METHOD_NOT_SUPPORTED(10006, "不支持当前请求方法"),
	/**
	 * 不支持当前媒体类型
	 */
	MEDIA_TYPE_NOT_SUPPORTED(10007, "不支持当前媒体类型"),
	/**
	 * 不接受的媒体类型
	 */
	MEDIA_TYPE_NOT_ACCEPT(10008, "不接受的媒体类型"),
	/**
	 * 请求被拒绝
	 */
	REQ_REJECT(10009, "请求被拒绝"),

	//------------------------------通用数据层错误码------------------------------//
	/**
	 * 数据不存在
	 */
	DATA_NOT_EXIST(10100, "数据不存在"),
	/**
	 * 数据已存在
	 */
	DATA_EXISTED(10101, "数据已存在"),
	/**
	 * 数据添加失败
	 */
	DATA_ADD_FAILED(10102, "数据添加失败"),
	/**
	 * 数据更新失败
	 */
	DATA_UPDATE_FAILED(10103, "数据更新失败"),
	/**
	 * 数据删除失败
	 */
	DATA_DELETE_FAILED(10104, "数据删除失败"),
	;

	private final int code;
	private final String msg;


}
