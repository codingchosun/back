package com.codingchosun.backend.exception.invalidrequest;

public class DeletedUserException extends RuntimeException {
    public DeletedUserException(String message) {super(message);}
}
