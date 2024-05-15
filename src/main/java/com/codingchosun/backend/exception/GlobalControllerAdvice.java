package com.codingchosun.backend.exception;

import com.codingchosun.backend.response.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler({ EntityNotFoundFromDB.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResult entityNotFoundHandler(EntityNotFoundFromDB e) {
        //log.warn("Exception: ", e);   //에러 메시지 너무 많이 나옴
        return new ErrorResult("NOT_FOUND", e.getMessage());
    }
}
