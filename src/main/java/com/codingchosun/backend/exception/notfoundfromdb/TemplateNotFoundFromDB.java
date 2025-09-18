package com.codingchosun.backend.exception.notfoundfromdb;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class TemplateNotFoundFromDB extends ApiException {
    public TemplateNotFoundFromDB(ErrorCode errorCode) {
        super(errorCode);
    }
}
