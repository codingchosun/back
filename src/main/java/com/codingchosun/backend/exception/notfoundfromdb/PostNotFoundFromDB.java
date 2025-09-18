package com.codingchosun.backend.exception.notfoundfromdb;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class PostNotFoundFromDB extends ApiException {
    public PostNotFoundFromDB(ErrorCode errorCode) {
        super(errorCode);
    }
}
