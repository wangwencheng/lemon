package org.lemon.common.core.util;

import org.lemon.common.core.constant.enums.ErrorCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 统一响应体
 * @author wwc
 */
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(description = "响应信息主体")
public class R<T> implements Serializable {

	private static final long serialVersionUID = -6155809858596159858L;

	@Getter
	@Setter
	@ApiModelProperty(value = "返回代码：成功=0，失败=-1")
	private int code;

	@Getter
	@Setter
	@ApiModelProperty(value = "返回信息")
	private String msg;

	@Getter
	@Setter
	@ApiModelProperty(value = "数据")
	private T data;

	public static <T> R<T> ok() {
		return restResult(null, ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMsg());
	}

	public static <T> R<T> ok(T data) {
		return restResult(data, ErrorCodeEnum.SUCCESS.getCode(), ErrorCodeEnum.SUCCESS.getMsg());
	}

	public static <T> R<T> ok(T data, String msg) {
		return restResult(data, ErrorCodeEnum.SUCCESS.getCode(), msg);
	}

	public static <T> R<T> failed() {
		return restResult(null, ErrorCodeEnum.FAIL.getCode(), ErrorCodeEnum.FAIL.getMsg());
	}

	public static <T> R<T> failed(String msg) {
		return restResult(null, ErrorCodeEnum.FAIL.getCode(), msg);
	}

	public static <T> R<T> failed(T data) {
		return restResult(data, ErrorCodeEnum.FAIL.getCode(), ErrorCodeEnum.FAIL.getMsg());
	}

	public static <T>R<T> failed(int code, String msg){
		return restResult(null, code, msg);
	}

	public static <T>R<T> failed(IErrorCode errorCode){
		return restResult(null, errorCode.getCode(), errorCode.getMsg());
	}

	public static <T> R<T> failed(T data, String msg) {
		return restResult(data, ErrorCodeEnum.FAIL.getCode(), msg);
	}

	private static <T> R<T> restResult(T data, int code, String msg) {
		R<T> apiResult = new R<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setMsg(msg);
		return apiResult;
	}

}
