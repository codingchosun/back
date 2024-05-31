package com.codingchosun.backend.exception.invalidrequest;

public class NotJoinedPost extends RuntimeException{
    public NotJoinedPost(String message) {
        super(message);
    }
}
