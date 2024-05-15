package com.codingchosun.backend.exception;

public class PostNotFoundFromDB extends EntityNotFoundFromDB{
    public PostNotFoundFromDB(String message, Throwable cause) {
        super(message, cause);
    }

    public PostNotFoundFromDB(String message) {
        super(message);
    }
}
