package com.codingchosun.backend.exception.localcontrolleradvice;

import com.codingchosun.backend.constants.ExceptionConstants;
import com.codingchosun.backend.controller.PostController;
import com.codingchosun.backend.exception.GlobalControllerAdvice;
import com.codingchosun.backend.exception.invalidrequest.AlreadyJoinedPost;
import com.codingchosun.backend.exception.invalidrequest.IsNotPostHash;
import com.codingchosun.backend.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = PostController.class)
public class PostControllerAdvice {

    @ExceptionHandler(value = IsNotPostHash.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<GlobalControllerAdvice.ExceptionDto> isNotPostHashHandler(IsNotPostHash e) {
        log.warn(ExceptionConstants.PROCESSED);
        GlobalControllerAdvice.ExceptionDto exceptionDto = new GlobalControllerAdvice.ExceptionDto("포스트에 존재하지 않는 해쉬태그입니다", e.getMessage());
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, false, exceptionDto);
    }

    @ExceptionHandler(value = AlreadyJoinedPost.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<GlobalControllerAdvice.ExceptionDto> alreadyJoinedPostHandler(AlreadyJoinedPost e) {
        log.warn(ExceptionConstants.PROCESSED);
        GlobalControllerAdvice.ExceptionDto exceptionDto = new GlobalControllerAdvice.ExceptionDto("중복 참가",e.getMessage());
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, false, exceptionDto);
    }
}
