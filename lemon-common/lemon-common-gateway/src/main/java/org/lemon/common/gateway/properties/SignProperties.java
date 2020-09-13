package org.lemon.common.gateway.properties;

import lombok.Data;

/**
 * 签名配置
 * @author Donald
 */
@Data
public class SignProperties {

	private boolean enable = true;

	private String type = "MD5";

	private String secretKey;
}
