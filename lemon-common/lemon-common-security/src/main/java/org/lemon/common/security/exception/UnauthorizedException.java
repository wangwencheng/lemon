package org.lemon.common.security.exception;

import org.lemon.common.security.component.GbAuth2ExceptionSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;


/**
 * @author Donald
 */
@JsonSerialize(using = GbAuth2ExceptionSerializer.class)
public class UnauthorizedException extends GbAuth2Exception {

	public UnauthorizedException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "unauthorized";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.UNAUTHORIZED.value();
	}

}
