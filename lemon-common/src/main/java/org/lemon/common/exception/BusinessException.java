package org.lemon.common.exception;

import lombok.Data;
import org.lemon.common.constant.enums.ErrorCodeEnum;

/**
 * 业务异常
 *
 * @author Donald
 */
@Data
public class BusinessException extends RuntimeException {

    private int code = ErrorCodeEnum.FAIL.getCode();

    public BusinessException(String msg) {
        super(msg);
    }

    public BusinessException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public BusinessException(int code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
    }

    public BusinessException(int code, String format, Object... args) {
        super(String.format(format, args));
        this.code = code;
    }

    public BusinessException(Throwable cause) {
        this(cause.getMessage(), cause);
    }

    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
