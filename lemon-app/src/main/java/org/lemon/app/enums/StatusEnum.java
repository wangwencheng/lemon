package org.lemon.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ErvinWang
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {
    ENABLE(1, "启用"),
    DISABLE(0, "停用");
    private Integer code;
    private String msg;
}
