package com.codingchosun.backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private Boolean success;
    private String message;
    private T body;

    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse<>(true, "OK", data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(true, "CREATED", data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ApiResponse<>(false, message, null));
    }
}
