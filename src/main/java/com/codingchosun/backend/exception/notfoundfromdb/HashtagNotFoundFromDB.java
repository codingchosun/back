package com.codingchosun.backend.exception.notfoundfromdb;


import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class HashtagNotFoundFromDB extends ApiException {
    public HashtagNotFoundFromDB(ErrorCode errorCode) {
        super(errorCode);
    }
}
