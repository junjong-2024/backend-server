package com.debait.debait.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleInternalServerError(Exception ex, WebRequest request) {
        // 로그인 관련 예외는 401으로 처리
        if (isAuthenticationException(ex)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Authentication Error");
        }

        // 그 외의 모든 예외는 500으로 처리
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal Server Error");
    }

    private boolean isAuthenticationException(Exception ex) {
        // 예외가 로그인 관련 예외인지 확인
        return ex.getClass().isAssignableFrom(AuthenticationException.class);
    }
}
