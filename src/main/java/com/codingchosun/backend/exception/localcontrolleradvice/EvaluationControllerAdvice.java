package com.codingchosun.backend.exception.localcontrolleradvice;

import com.codingchosun.backend.constants.ExceptionConstants;
import com.codingchosun.backend.controller.EvaluationController;
import com.codingchosun.backend.exception.GlobalControllerAdvice;
import com.codingchosun.backend.exception.invalidrequest.AlreadyValidated;
import com.codingchosun.backend.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = {EvaluationController.class})
public class EvaluationControllerAdvice {

    @ExceptionHandler(value = AlreadyValidated.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<GlobalControllerAdvice.ExceptionDto> alreadyEvaluationHandler(AlreadyValidated e) {
        log.warn(ExceptionConstants.PROCESSED);
        GlobalControllerAdvice.ExceptionDto exceptionDto = new GlobalControllerAdvice.ExceptionDto("중복 평가 오류", e.getMessage());

        return new ApiResponse<>(HttpStatus.BAD_REQUEST, false, exceptionDto);
    }
}
