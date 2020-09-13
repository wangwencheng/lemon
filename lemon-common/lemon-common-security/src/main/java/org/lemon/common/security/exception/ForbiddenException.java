package org.lemon.common.security.exception;

import org.lemon.common.security.component.GbAuth2ExceptionSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;


/**
 * @author Donald
 */
@JsonSerialize(using = GbAuth2ExceptionSerializer.class)
public class ForbiddenException extends GbAuth2Exception {

	public ForbiddenException(String msg) {
		super(msg);
	}

	public ForbiddenException(String msg, Throwable t) {
		super(msg,t);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "access_denied";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.FORBIDDEN.value();
	}

}

