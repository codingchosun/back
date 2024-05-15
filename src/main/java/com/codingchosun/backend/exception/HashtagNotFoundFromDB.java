package com.codingchosun.backend.exception;


public class HashtagNotFoundFromDB extends EntityNotFoundFromDB {
    public HashtagNotFoundFromDB(String message, Throwable cause) {
        super(message, cause);
    }

    public HashtagNotFoundFromDB(String message) {
        super(message);
    }
}
