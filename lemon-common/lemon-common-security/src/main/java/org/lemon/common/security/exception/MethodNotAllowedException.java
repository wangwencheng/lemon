package org.lemon.common.security.exception;

import org.lemon.common.security.component.LemonAuth2ExceptionSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;


/**
 * @author wwc
 */
@JsonSerialize(using = LemonAuth2ExceptionSerializer.class)
public class MethodNotAllowedException extends LemonAuth2Exception {

	public MethodNotAllowedException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "method_not_allowed";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.METHOD_NOT_ALLOWED.value();
	}

}
