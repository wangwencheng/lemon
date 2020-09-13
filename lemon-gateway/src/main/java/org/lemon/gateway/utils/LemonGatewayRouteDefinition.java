package org.lemon.gateway.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class LemonGatewayRouteDefinition {
    // 路由的 Id
    private String id;
    // 路由断言集合配置
    private List<LemonGatewayPredicateDefinition> predicates = new ArrayList<>();
    // 路由过滤器集合配置
    private List<LemonGatewayFilterDefinition> filters = new ArrayList<>();
    // 路由规则转发的目标 uri
    private String uri;
    // 路由执行的顺序
    private int order = 0;
    // 此处省略 get 和 set 方法
}
