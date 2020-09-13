package org.lemon.common.security.exception;

import org.lemon.common.security.component.GbAuth2ExceptionSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * @author Donald
 */
@JsonSerialize(using = GbAuth2ExceptionSerializer.class)
public class InvalidException extends GbAuth2Exception {

	public InvalidException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "invalid_exception";
	}

	@Override
	public int getHttpErrorCode() {
		return 426;
	}

}
