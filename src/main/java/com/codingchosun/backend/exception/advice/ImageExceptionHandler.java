package com.codingchosun.backend.exception.advice;

import com.codingchosun.backend.controller.image.ImageController;
import com.codingchosun.backend.dto.response.ApiResponse;
import com.codingchosun.backend.dto.response.ErrorResponse;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.invalidrequest.NotPostImageException;
import com.codingchosun.backend.exception.invalidrequest.UnauthorizedActionException;
import com.codingchosun.backend.exception.notfoundfromdb.ImageNotFoundFromDB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = ImageController.class)
public class ImageExceptionHandler {

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleUnauthorizedAction(UnauthorizedActionException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("Image 작업 권한 예외: {}", errorCode.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());

        return ApiResponse.error(errorCode.getStatus(), errorResponse.getMessage());
    }

    @ExceptionHandler(ImageNotFoundFromDB.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleImageNotFound(ImageNotFoundFromDB e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("Image 엔티티 예외: {}", errorCode.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());

        return ApiResponse.error(errorCode.getStatus(), errorResponse.getMessage());
    }

    @ExceptionHandler(NotPostImageException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleNotPostImage(NotPostImageException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("Image 게시글 예외: {}", errorCode.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());

        return ApiResponse.error(errorCode.getStatus(), errorResponse.getMessage());
    }

}
