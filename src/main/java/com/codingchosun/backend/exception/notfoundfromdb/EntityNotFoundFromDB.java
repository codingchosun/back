package com.codingchosun.backend.exception.notfoundfromdb;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class EntityNotFoundFromDB extends ApiException {

    public EntityNotFoundFromDB(ErrorCode errorCode) {
        super(errorCode);
    }

}
