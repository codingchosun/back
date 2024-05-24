package com.codingchosun.backend.exception;

import org.springframework.http.HttpStatus;

public class NotEqualsUserSize extends ApiException {
    public NotEqualsUserSize(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
