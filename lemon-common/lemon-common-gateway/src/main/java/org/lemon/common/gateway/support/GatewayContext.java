package org.lemon.common.gateway.support;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Gateway Context Use Cache Request Content
 * @author Donald
 */
@Getter
@Setter
@ToString
public class GatewayContext {

	/**
	 * cache json body
	 */
	protected String requestBody;
	/**
	 * cache Response Body
	 */
	protected Object responseBody;
	/**
	 * request headers
	 */
	protected HttpHeaders requestHeaders;
	/**
	 * cache form data
	 */
	protected MultiValueMap<String, String> formData;
	/**
	 * cache all request data include:form data and query param
	 */
	protected MultiValueMap<String, String> allRequestData = new LinkedMultiValueMap<>(0);
}
