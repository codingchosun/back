package com.codingchosun.backend.exception.emptyrequest;

/*
* 필요한 값이 없는 요청이 왔을때 발생하는 예외
*/

public class EmptyRequestException extends RuntimeException{
    public EmptyRequestException(String message) {
        super(message);
    }
}
