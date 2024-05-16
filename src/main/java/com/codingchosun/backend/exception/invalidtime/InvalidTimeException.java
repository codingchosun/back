package com.codingchosun.backend.exception.invalidtime;

/*
* 시간 설정이 잘못됐을때 발생하는 예외
*/
public class InvalidTimeException extends RuntimeException{
    public InvalidTimeException(String message) {
        super(message);
    }
}
