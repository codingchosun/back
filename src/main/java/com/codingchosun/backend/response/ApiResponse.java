package com.codingchosun.backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/*
    컨트롤러 처리의 결과를 담을 객체
 */
@Data
@AllArgsConstructor
public class ApiResponse<T> {
    //http 상태코드
    private HttpStatus httpStatus;
    //처리 결과의 성공 여부
    private Boolean success;
    //첨부할 데이터와 메시지들
    private T body;
}
