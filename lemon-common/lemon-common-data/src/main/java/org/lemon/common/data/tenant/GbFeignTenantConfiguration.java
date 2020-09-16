package org.lemon.common.data.tenant;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign租户信息拦截配置
 * @author wwc
 */
@Configuration
public class GbFeignTenantConfiguration {
	@Bean
	public RequestInterceptor gbFeignTenantInterceptor() {
		return new GbFeignTenantInterceptor();
	}
}
