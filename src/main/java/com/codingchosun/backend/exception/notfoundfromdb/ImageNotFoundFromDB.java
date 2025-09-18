package com.codingchosun.backend.exception.notfoundfromdb;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class ImageNotFoundFromDB extends ApiException {
    public ImageNotFoundFromDB(ErrorCode errorCode) {
        super(errorCode);
    }
}
