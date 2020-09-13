package org.lemon.gateway.utils;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class LemonGatewayPredicateDefinition {
    // 断言对应的 Name
    private String name;
    // 配置的断言规则
    private Map<String, String> args = new LinkedHashMap<>();
    // 此处省略 Get 和 Set 方法
}