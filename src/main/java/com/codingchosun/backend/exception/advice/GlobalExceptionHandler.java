package com.codingchosun.backend.exception.advice;

import com.codingchosun.backend.dto.response.ApiResponse;
import com.codingchosun.backend.dto.response.ErrorResponse;
import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBusinessException(ApiException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("비즈니스 예외: {}", errorCode.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(new ApiResponse<>(errorCode.getStatus(), false, errorResponse), errorCode.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ErrorCode errorCode = ErrorCode.INVALID_INPUT_VALUE;
        log.warn("@Valid 예외: {}", errorCode.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(new ApiResponse<>(errorCode.getStatus(), false, errorResponse), errorCode.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleAllUncaughtException(Exception e) {
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;
        log.error("예측하지 못한 예외:", e);
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(new ApiResponse<>(errorCode.getStatus(), false, errorResponse), errorCode.getStatus());
    }
}
