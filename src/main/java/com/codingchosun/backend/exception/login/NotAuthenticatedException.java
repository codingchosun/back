package com.codingchosun.backend.exception.login;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class NotAuthenticatedException extends ApiException {
    public NotAuthenticatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
