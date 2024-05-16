package com.codingchosun.backend.exception.emptyrequest;

/*
* 댓글작성에서 댓글이 비었을때 발생하는 예외
*/
public class EmptyCommentException extends EmptyRequestException{
    public EmptyCommentException(String message) {
        super(message);
    }
}
