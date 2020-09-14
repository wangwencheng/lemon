package org.lemon.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.lemon.common.core.exception.BusinessException;
import org.lemon.common.core.util.R;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * OAuth2异常处理器
 */
@Slf4j
@Order
@RestControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class OAuth2ExceptionHandler {

    /**
     * InternalAuthenticationServiceException异常
     * oauth2对抛出的异常再做了一层封装,需要特殊处理
     * 否则会返回状态码为-1
     */
    @ExceptionHandler({InvalidGrantException.class, InternalAuthenticationServiceException.class})
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R handleError(Exception e) {
        log.error("全局异常信息 ex={}", e.getMessage(), e);
        if (e.getCause() instanceof BusinessException) {
            return R.failed(((BusinessException) e.getCause()).getCode(), e.getLocalizedMessage());
        }
        return R.failed(e.getLocalizedMessage());
    }
}
