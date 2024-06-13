package com.codingchosun.backend.exception.invalidrequest;

public class AlreadyValidated extends RuntimeException{
    public AlreadyValidated(String message) {
        super(message);
    }
}
