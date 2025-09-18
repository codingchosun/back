package com.codingchosun.backend.exception.advice;

import com.codingchosun.backend.controller.post.MyPostController;
import com.codingchosun.backend.controller.post.PostController;
import com.codingchosun.backend.controller.post.PostParticipantController;
import com.codingchosun.backend.dto.response.ApiResponse;
import com.codingchosun.backend.dto.response.ErrorResponse;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.invalidrequest.InvalidPostHashtagException;
import com.codingchosun.backend.exception.invalidrequest.UserAlreadyParticipantException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = {PostController.class, MyPostController.class, PostParticipantController.class})
public class PostExceptionHandler {

    @ExceptionHandler(UserAlreadyParticipantException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleUserAlreadyParticipant(UserAlreadyParticipantException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("Post 예외: {}", errorCode.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(new ApiResponse<>(errorCode.getStatus(), false, errorResponse), errorCode.getStatus());
    }

    @ExceptionHandler(InvalidPostHashtagException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleInvalidPostHashtag(InvalidPostHashtagException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("PostHash 예외: {}", errorCode.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());

        return new ResponseEntity<>(new ApiResponse<>(errorCode.getStatus(), false, errorResponse), errorCode.getStatus());
    }

}
