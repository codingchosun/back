package com.codingchosun.backend.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
에러가 터졌을때 리턴될 dto입니다.
 */
@Data
@AllArgsConstructor
public class ErrorResult {
    private String code;
    private String message;
}
