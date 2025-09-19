package com.codingchosun.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

//컨트롤러 API 응답
@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private HttpStatus httpStatus;
    private Boolean success;
    private T body;

    //200 OK
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(HttpStatus.OK, true, data);
    }

    //201 CREATED
    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>(HttpStatus.CREATED, true, data);
    }


}
