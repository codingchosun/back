package com.codingchosun.backend.exception;

/*
*   컨트롤러에서 @Login으로 가져온 user가 널일 경우 발생하는 예외
*/
public class LoggedInUserNotFound extends RuntimeException{
    public LoggedInUserNotFound(String message) {
        super(message);
    }
}
