package com.codingchosun.backend.exception.invalidrequest;

public class YouAreNotAdmin extends RuntimeException{
    public YouAreNotAdmin(String message) {
        super(message);
    }
}
