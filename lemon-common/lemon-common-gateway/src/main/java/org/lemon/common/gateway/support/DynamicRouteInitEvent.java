package org.lemon.common.gateway.support;

import org.springframework.context.ApplicationEvent;

/**
 * 路由初始化事件
 * @author Donald
 */
public class DynamicRouteInitEvent extends ApplicationEvent {
	public DynamicRouteInitEvent(Object source) {
		super(source);
	}
}
