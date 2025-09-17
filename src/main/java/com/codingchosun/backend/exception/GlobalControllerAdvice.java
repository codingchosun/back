package com.codingchosun.backend.exception;

import com.codingchosun.backend.constants.ExceptionConstants;
import com.codingchosun.backend.exception.emptyrequest.EmptyRequestException;
import com.codingchosun.backend.exception.invalidrequest.InvalidEditorException;
import com.codingchosun.backend.exception.invalidtime.BeforeCurrentTimeException;
import com.codingchosun.backend.exception.notfoundfromdb.EntityNotFoundFromDB;
import com.codingchosun.backend.response.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.codingchosun.backend")
public class GlobalControllerAdvice {

    @ExceptionHandler({IllegalStateException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<ExceptionDto> illegalStateExceptionHandler(IllegalStateException e) {
        log.warn(ExceptionConstants.PROCESSED);
        ExceptionDto exceptionDto = new ExceptionDto("illegalStateException 발생", e.getMessage());
        return new ApiResponse<>(HttpStatus.NOT_FOUND, false, exceptionDto);
    }

    @ExceptionHandler({LoggedInUserNotFound.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ExceptionDto> loggedInUserNotFoundHandler(LoggedInUserNotFound e) {
        log.warn(ExceptionConstants.LOGGED_IN_USER_NOT_FOUND);
        ExceptionDto exceptionDto = new ExceptionDto("login여부를 확인하세요", e.getMessage());
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, false, exceptionDto);
    }

    @ExceptionHandler({EmptyRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ExceptionDto> emptyRequestExceptionHandler(EmptyRequestException e) {
        log.warn(ExceptionConstants.EMPTY_CONTENTS);
        ExceptionDto exceptionDto = new ExceptionDto("요청이 비어있습니다.", e.getMessage());
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, false, exceptionDto);
    }


    @ExceptionHandler({EntityNotFoundFromDB.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ApiResponse<ExceptionDto> entityNotFoundHandler(EntityNotFoundFromDB e) {
        log.warn(ExceptionConstants.PROCESSED);
        ExceptionDto exceptionDto = new ExceptionDto("db에 없는 데이터입니다.", e.getMessage());
        return new ApiResponse<>(HttpStatus.NOT_FOUND, false, exceptionDto);
    }

    @ExceptionHandler({BeforeCurrentTimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiResponse<ExceptionDto> timeBeforeCurrentExceptionHandler(BeforeCurrentTimeException e) {
        ExceptionDto exceptionDto = new ExceptionDto("현재시간보다 이른 약속시간을 설정할수없습니다.", e.getMessage());
        log.warn(ExceptionConstants.PROCESSED);
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, false, exceptionDto);
    }

    @ExceptionHandler({MissingValueException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiResponse<ExceptionDto> wrongRequestEntity(MissingValueException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage(), e.getMessage());
        log.warn(ExceptionConstants.PROCESSED);
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, false, exceptionDto);
    }

    @ExceptionHandler({ObjectNotFound.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiResponse<ExceptionDto> objectNotFound(ObjectNotFound e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage(), e.getMessage());
        log.warn(ExceptionConstants.PROCESSED);
        return new ApiResponse<>(HttpStatus.NOT_FOUND, false, exceptionDto);
    }

    @ExceptionHandler({NotEqualsUserSize.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiResponse<ExceptionDto> notEqualsUserSize(ObjectNotFound e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage(), e.getMessage());
        log.warn(ExceptionConstants.PROCESSED);
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, false, exceptionDto);
    }

    @ExceptionHandler({InvalidEditorException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ApiResponse<ExceptionDto> invalidEditorException(InvalidEditorException e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage(), e.getMessage());
        log.warn(ExceptionConstants.PROCESSED);
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, false, exceptionDto);
    }

    @ExceptionHandler({ExistLoginId.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    protected ApiResponse<ExceptionDto> existLoginIdException(ExistLoginId e) {
        ExceptionDto exceptionDto = new ExceptionDto(e.getMessage(), e.getMessage());
        log.warn(ExceptionConstants.PROCESSED);
        return new ApiResponse<>(HttpStatus.CONFLICT, false, exceptionDto);
    }

    @Data
    @AllArgsConstructor
    public static class ExceptionDto {
        //넣고 싶은 메시지
        private String message;

        //예외의 메시지
        private String exceptionMessage;
    }
}
