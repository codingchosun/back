package com.codingchosun.backend.exception.notfoundfromdb;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class CommentNotFoundFromDB extends ApiException {
    public CommentNotFoundFromDB(ErrorCode errorCode) {
        super(errorCode);
    }
}
