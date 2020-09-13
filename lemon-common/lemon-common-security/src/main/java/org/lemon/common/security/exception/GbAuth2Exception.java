package org.lemon.common.security.exception;

import org.lemon.common.security.component.GbAuth2ExceptionSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义OAuth2Exception
 * @author Donald
 */
@JsonSerialize(using = GbAuth2ExceptionSerializer.class)
public class GbAuth2Exception extends OAuth2Exception {
	@Getter
	private String errorCode;

	public GbAuth2Exception(String msg) {
		super(msg);
	}

	public GbAuth2Exception(String msg, Throwable t) {
		super(msg,t);
	}

	public GbAuth2Exception(String msg, String errorCode) {
		super(msg);
		this.errorCode = errorCode;
	}
}
