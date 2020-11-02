package org.lemon.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ErvinWang
 */

@Getter
@AllArgsConstructor
public enum RecStatusEnum {
    DELETED(1, "删除"),
    UNDELETED(0, "未删除");
    private Integer code;
    private String msg;
}
