package org.lemon.common.core.handler;

import org.lemon.common.core.constant.enums.ErrorCodeEnum;
import org.lemon.common.core.util.R;
import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

/**
 * 处理可预见的异常
 *
 * @author wwc
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@RestControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class RestExceptionHandler {

	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public R handleError(MissingServletRequestParameterException e) {
		log.error("缺少请求参数 ex={}", e.getMessage(), e);
		String message = String.format("缺少必要的请求参数: %s", e.getParameterName());
		return R.failed(ErrorCodeEnum.PARAM_MISS.getCode(), message);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public R handleError(MethodArgumentTypeMismatchException e) {
		log.error("请求参数格式错误 ex={}", e.getMessage(), e);
		String message = String.format("请求参数格式错误: %s", e.getName());
		return R.failed(ErrorCodeEnum.PARAM_TYPE_ERROR.getCode(), message);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public R handleError(MethodArgumentNotValidException e) {
		log.error("参数验证失败 ex={}", e.getMessage(), e);
		FieldError error = e.getBindingResult().getFieldError();
		String message = String.format("%s:%s", error.getField(), error.getDefaultMessage());
		return R.failed(ErrorCodeEnum.PARAM_BIND_ERROR.getCode(), message);
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public R handleError(BindException e) {
		log.error("参数绑定失败 ex={}", e.getMessage(), e);
		FieldError error = e.getBindingResult().getFieldError();
		String message = String.format("%s:%s", error.getField(), error.getDefaultMessage());
		return R.failed(ErrorCodeEnum.PARAM_BIND_ERROR.getCode(), message);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public R handleError(HttpMessageNotReadableException e) {
		log.error("消息不能读取 ex={}", e.getMessage(), e);
		return R.failed(ErrorCodeEnum.MSG_NOT_READABLE.getCode(), e.getMessage());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public R handleError(HttpRequestMethodNotSupportedException e) {
		log.error("不支持当前请求方法 ex={}", e.getMessage(), e);
		return R.failed(ErrorCodeEnum.METHOD_NOT_SUPPORTED.getCode(), e.getMessage());
	}

	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public R handleError(HttpMediaTypeNotSupportedException e) {
		log.error("不支持当前媒体类型 ex={}", e.getMessage(), e);
		return R.failed(ErrorCodeEnum.MEDIA_TYPE_NOT_SUPPORTED.getCode(), e.getMessage());
	}

	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public R handleError(HttpMediaTypeNotAcceptableException e) {
		log.error("不接受的媒体类型 ex={}", e.getMessage(), e);
		String message = e.getMessage() + " " + ArrayUtil.join(e.getSupportedMediaTypes(), ",");
		return R.failed(ErrorCodeEnum.MEDIA_TYPE_NOT_SUPPORTED.getCode(), message);
	}
}
