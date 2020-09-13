package org.lemon.common.data.tenant;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.core.constant.CommonConstant;

/**
 * feign租户信息拦截
 * @author Donald
 */
@Slf4j
public class GbFeignTenantInterceptor implements RequestInterceptor {
	@Override
	public void apply(RequestTemplate requestTemplate) {
		if (TenantContextHolder.getTenantId() == null) {
			log.error("TTL中的租户ID为空，feign拦截器 >> 增强失败");
			return;
		}
		requestTemplate.header(CommonConstant.TENANT_ID, TenantContextHolder.getTenantId().toString());
	}
}
