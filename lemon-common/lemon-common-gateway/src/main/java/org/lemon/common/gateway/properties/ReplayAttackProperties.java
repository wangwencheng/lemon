package org.lemon.common.gateway.properties;

import lombok.Data;

/**
 * 防重放攻击配置
 * @author wwc
 */
@Data
public class ReplayAttackProperties {
	private boolean enable = true;
	private int timeout = 60;
}
