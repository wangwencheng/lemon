package org.lemon.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ErvinWang
 */
@Data
@AllArgsConstructor
public class R<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> R<T> success(Integer code) {
        return new R<T>(code, "操作成功", null);
    }

    public static <T> R<T> success(String msg) {
        return new R<T>(0, "操作成功", null);
    }

    public static <T> R<T> success(String msg, T data) {
        return new R<T>(0, "操作成功", data);
    }

    public static <T> R<T> failed(Integer code, String msg) {
        return new R<T>(code, msg, null);
    }
}
