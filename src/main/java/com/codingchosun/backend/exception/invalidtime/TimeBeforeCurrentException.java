package com.codingchosun.backend.exception.invalidtime;

/*
* 설정한 약속시간이 현재 시간보다 이전이라면 발생하는 예외입니다.
*/
public class TimeBeforeCurrentException extends InvalidTimeException{
    public TimeBeforeCurrentException(String message) {
        super(message);
    }
}
