package com.codingchosun.backend.exception.advice;

import com.codingchosun.backend.controller.evaluation.EvaluationController;
import com.codingchosun.backend.dto.response.ApiResponse;
import com.codingchosun.backend.dto.response.ErrorResponse;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.invalidrequest.AlreadyEvaluatedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = {EvaluationController.class})
public class EvaluationExceptionHandler {

    @ExceptionHandler(AlreadyEvaluatedException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleAlreadyEvaluated(AlreadyEvaluatedException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("평가 예외: {}", errorCode.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());

        return ApiResponse.error(errorCode.getStatus(), errorResponse.getMessage());
    }
}
