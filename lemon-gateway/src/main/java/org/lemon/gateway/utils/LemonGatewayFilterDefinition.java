package org.lemon.gateway.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class LemonGatewayFilterDefinition {
    //Filter Name
    private String name;
    // 对应的路由规则
    private Map<String, String> args = new LinkedHashMap<>();
    // 此处省略 Get 和 Set 方法
}