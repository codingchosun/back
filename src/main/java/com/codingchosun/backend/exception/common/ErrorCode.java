package com.codingchosun.backend.exception.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    //Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력 값입니다."),
    UNAUTHORIZED_ACTION(HttpStatus.FORBIDDEN, "C002", "작업 권한이 없습니다."),
    INVALID_TIME_SETUP(HttpStatus.BAD_REQUEST, "C003", "설정 시간이 유효하지 않습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C004", "서버 내부 오류가 발생했습니다."),
    FILE_STORAGE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "C005", "파일 저장에 실패했습니다."),

    //User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "존재하지 않는 사용자입니다."),
    LOGIN_ID_DUPLICATION(HttpStatus.BAD_REQUEST, "U002", "이미 존재하는 아이디입니다."),
    AUTHENTICATION_REQUIRED(HttpStatus.UNAUTHORIZED, "U003", "로그인이 필요한 기능입니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "U004", "로그인 처리에 문제가 발생했습니다."),
    DELETED_USER(HttpStatus.BAD_REQUEST, "U005", "탈퇴한 사용자입니다."),

    //Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "P001", "존재하지 않는 게시글입니다."),
    USER_NOT_PARTICIPANT(HttpStatus.BAD_REQUEST, "P002", "모임의 참여자가 아닙니다"),
    USER_ALREADY_PARTICIPANT(HttpStatus.CONFLICT, "P003", "이미 모임에 참가한 사용자입니다."),
    INVALID_POST_HASHTAG(HttpStatus.BAD_REQUEST, "P004", "유효하지 않은 게시글 해시태그입니다."),

    //Comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "존재하지 않는 댓글입니다."),

    //Image
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "I001", "존재하지 않는 이미지입니다."),
    NOT_POST_IMAGE(HttpStatus.BAD_REQUEST, "I002", "해당 게시글의 이미지가 아닙니다."),

    // Evaluation
    ALREADY_EVALUATED(HttpStatus.BAD_REQUEST, "E001", "이미 평가를 완료한 항목입니다."),

    //Hashtag
    HASHTAG_NOT_FOUND(HttpStatus.NOT_FOUND, "H001", "존재하지 않은 해시태그입니다."),

    //Template
    TEMPLATE_NOT_FOUND(HttpStatus.NOT_FOUND, "T001", "존재하지 않은 템플릿입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;


}
