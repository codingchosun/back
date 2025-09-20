package com.codingchosun.backend.exception.advice;

import com.codingchosun.backend.controller.user.*;
import com.codingchosun.backend.dto.response.ApiResponse;
import com.codingchosun.backend.dto.response.ErrorResponse;
import com.codingchosun.backend.exception.common.ErrorCode;
import com.codingchosun.backend.exception.invalidrequest.DeletedUserException;
import com.codingchosun.backend.exception.login.DuplicationLoginIdException;
import com.codingchosun.backend.exception.login.LoginProcessFailedException;
import com.codingchosun.backend.exception.login.NotAuthenticatedException;
import com.codingchosun.backend.exception.login.NotMatchPasswordException;
import com.codingchosun.backend.exception.notfoundfromdb.UserNotFoundFromDB;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackageClasses = {DeleteAccountController.class, FindUserController.class, LoginCheckController.class, LoginController.class, ProfileController.class, SignUpController.class})
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundFromDB.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleUserNotFoundFromDB(UserNotFoundFromDB e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("회원 찾기 예외: {}", errorCode.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());

        return ApiResponse.error(errorCode.getStatus(), errorResponse.getMessage());
    }

    @ExceptionHandler(LoginProcessFailedException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleLoginProcessFailed(LoginProcessFailedException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("로그인 예외: {}", errorCode.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());

        return ApiResponse.error(errorCode.getStatus(), errorResponse.getMessage());
    }

    @ExceptionHandler(NotAuthenticatedException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleUserNotAuthenticated(NotAuthenticatedException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("로그인 권한 예외: {}", errorCode.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());

        return ApiResponse.error(errorCode.getStatus(), errorResponse.getMessage());
    }

    @ExceptionHandler(DuplicationLoginIdException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleUserDuplicationLoginId(DuplicationLoginIdException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("중복된 아이디 예외: {}", errorCode.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());

        return ApiResponse.error(errorCode.getStatus(), errorResponse.getMessage());
    }

    @ExceptionHandler(NotMatchPasswordException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleNotMatchPassword(NotMatchPasswordException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("불일치 예외: {}", errorCode.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());

        return ApiResponse.error(errorCode.getStatus(), errorResponse.getMessage());
    }

    @ExceptionHandler(DeletedUserException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleDeletedUser(DeletedUserException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.warn("회원 탈퇴 예외: {}", errorCode.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getMessage());

        return ApiResponse.error(errorCode.getStatus(), errorResponse.getMessage());
    }
}
