package org.lemon.common.security.exception;

import org.lemon.common.security.component.LemonAuth2ExceptionSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义OAuth2Exception
 * @author wwc
 */
@JsonSerialize(using = LemonAuth2ExceptionSerializer.class)
public class LemonAuth2Exception extends OAuth2Exception {
	@Getter
	private String errorCode;

	public LemonAuth2Exception(String msg) {
		super(msg);
	}

	public LemonAuth2Exception(String msg, Throwable t) {
		super(msg,t);
	}

	public LemonAuth2Exception(String msg, String errorCode) {
		super(msg);
		this.errorCode = errorCode;
	}
}
