package com.codingchosun.backend.exception.localcontrolleradvice;

import com.codingchosun.backend.constants.ExceptionConstants;
import com.codingchosun.backend.controller.ImageController;
import com.codingchosun.backend.exception.GlobalControllerAdvice;
import com.codingchosun.backend.exception.invalidrequest.IsNotPostAuthor;
import com.codingchosun.backend.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = ImageController.class)
public class ImageControllerAdvice {

    @ExceptionHandler(value = IsNotPostAuthor.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<GlobalControllerAdvice.ExceptionDto> isNotPostHandler(IsNotPostAuthor e) {
        log.warn(ExceptionConstants.PROCESSED);
        GlobalControllerAdvice.ExceptionDto exceptionDto = new GlobalControllerAdvice.ExceptionDto("글작성자만이 사진을 올릴수있습니다", e.getMessage());
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, false, exceptionDto);
    }
}
