package org.lemon.common.handler;

import lombok.extern.slf4j.Slf4j;
import org.lemon.common.constant.enums.ErrorCodeEnum;
import org.lemon.common.exception.BusinessException;
import org.lemon.common.util.R;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author Donald
 */
@Slf4j
@Order
@RestControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class GlobalExceptionHandler {

    /**
     * 全局异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleError(Exception e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);
        return R.failed(ErrorCodeEnum.FAIL.getCode(), e.getLocalizedMessage());
    }

    /**
     * 业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleError(BusinessException e) {
        log.error("业务异常信息 ex={}", e.getMessage(), e);
        return R.failed(e.getCode(), e.getMessage());
    }


}
