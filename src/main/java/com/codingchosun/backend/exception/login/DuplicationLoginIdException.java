package com.codingchosun.backend.exception.login;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class DuplicationLoginIdException extends ApiException {
    public DuplicationLoginIdException(ErrorCode errorCode) {
        super(errorCode);
    }
}
