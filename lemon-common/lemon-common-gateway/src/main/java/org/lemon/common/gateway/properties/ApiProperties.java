package org.lemon.common.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * 接口配置
 * @author wwc
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiProperties {
	private Set<String> ignoreCheckUrls;
	private boolean responseMerge = true;
	private SignProperties sign = new SignProperties();
	private ReplayAttackProperties replayAttack = new ReplayAttackProperties();
}
