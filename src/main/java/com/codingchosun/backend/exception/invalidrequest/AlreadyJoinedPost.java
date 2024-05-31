package com.codingchosun.backend.exception.invalidrequest;

public class AlreadyJoinedPost extends RuntimeException{
    public AlreadyJoinedPost(String message) {
        super(message);
    }
}
