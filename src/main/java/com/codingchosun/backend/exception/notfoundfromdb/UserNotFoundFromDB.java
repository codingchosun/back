package com.codingchosun.backend.exception.notfoundfromdb;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;
import lombok.Getter;

@Getter
public class UserNotFoundFromDB extends ApiException {

    public UserNotFoundFromDB(ErrorCode errorCode) {
        super(errorCode);
    }
}
