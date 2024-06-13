package com.codingchosun.backend.exception;

import org.springframework.http.HttpStatus;

public class ExistLoginId extends ApiException {
    public ExistLoginId(String message, HttpStatus status) {
        super(message, status);
    }
}
