package com.codingchosun.backend.exception.localcontrolleradvice;

import com.codingchosun.backend.constants.ExceptionConstants;
import com.codingchosun.backend.controller.ImageController;
import com.codingchosun.backend.exception.GlobalControllerAdvice;
import com.codingchosun.backend.exception.invalidrequest.IsNotPostAuthor;
import com.codingchosun.backend.exception.invalidrequest.IsNotPostImage;
import com.codingchosun.backend.exception.notfoundfromdb.ImageNotFoundFromDB;
import com.codingchosun.backend.dto.response.ApiResponse;
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
        GlobalControllerAdvice.ExceptionDto exceptionDto = new GlobalControllerAdvice.ExceptionDto("글작성자가 아닙니다", e.getMessage());
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, false, exceptionDto);
    }

    @ExceptionHandler(value = ImageNotFoundFromDB.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<GlobalControllerAdvice.ExceptionDto> imageNotFoundFromDBHandler(ImageNotFoundFromDB e) {
        log.warn(ExceptionConstants.PROCESSED);
        GlobalControllerAdvice.ExceptionDto exceptionDto = new GlobalControllerAdvice.ExceptionDto("이미지 id에 맞는 entity를 못 찾았습니다", e.getMessage());
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, false, exceptionDto);
    }

    @ExceptionHandler(value = IsNotPostImage.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<GlobalControllerAdvice.ExceptionDto> isNotPostImageHandler(IsNotPostImage e) {
        log.warn(ExceptionConstants.PROCESSED);
        GlobalControllerAdvice.ExceptionDto exceptionDto = new GlobalControllerAdvice.ExceptionDto("이미지 id에 맞는 entity를 못 찾았습니다", e.getMessage());
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, false, exceptionDto);
    }
}
