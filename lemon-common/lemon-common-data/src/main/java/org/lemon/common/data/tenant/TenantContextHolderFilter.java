package org.lemon.common.data.tenant;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.lemon.common.core.constant.CommonConstant;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 租户请求过滤器
 *
 * @author Donald
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantContextHolderFilter extends GenericFilterBean {

	@Override
	@SneakyThrows
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String tenantId = request.getHeader(CommonConstant.TENANT_ID);
		log.debug("获取header中的租户ID为:{}", tenantId);

//		if (SecurityUtil.getUser() != null) {
//			tenantId = String.valueOf(SecurityUtil.getUser().getTenantId() == 0 ? "" : SecurityUtil.getUser().getTenantId());
//		}

		if (StrUtil.isNotBlank(tenantId)) {
			TenantContextHolder.setTenantId(Integer.parseInt(tenantId));
		}

		filterChain.doFilter(request, response);
		TenantContextHolder.clear();
	}
}
