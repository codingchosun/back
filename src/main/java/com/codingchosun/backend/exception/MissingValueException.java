package com.codingchosun.backend.exception;

import org.springframework.http.HttpStatus;

public class MissingValueException extends ApiException {
    public MissingValueException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
