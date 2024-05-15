package com.codingchosun.backend.exception;

public class EntityNotFoundFromDB extends RuntimeException {
    public EntityNotFoundFromDB(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundFromDB(String message) {
        super(message);
    }
}
