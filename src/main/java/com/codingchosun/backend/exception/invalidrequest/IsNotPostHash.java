package com.codingchosun.backend.exception.invalidrequest;

public class IsNotPostHash extends RuntimeException{
    public IsNotPostHash(String message) {
        super(message);
    }
}
