package com.codingchosun.backend.exception;

public class PasswordNotMatch extends RuntimeException {
    public PasswordNotMatch(String message) {
        super(message);
    }
}
