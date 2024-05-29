package com.codingchosun.backend.exception.localcontrolleradvice;

import com.codingchosun.backend.constants.ExceptionConstants;
import com.codingchosun.backend.exception.GlobalControllerAdvice;
import com.codingchosun.backend.exception.invalidrequest.IsNotPostHash;
import com.codingchosun.backend.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.codingchosun.backend.controller")    //컨트롤러를 패키지 단위로 관리하면 좋을듯 todo 다 같이 모였을때 이야기
public class PostControllerAdvice {

    @ExceptionHandler(value = IsNotPostHash.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<GlobalControllerAdvice.ExceptionDto> isNotPostHashHandler(IsNotPostHash e) {
        log.warn(ExceptionConstants.PROCESSED);
        GlobalControllerAdvice.ExceptionDto exceptionDto = new GlobalControllerAdvice.ExceptionDto("포스트에 존재하지 않는 해쉬태그입니다", e.getMessage(), e);
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, false, exceptionDto);
    }
}
