package com.codingchosun.backend.exception.login;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class NotMatchPasswordException extends ApiException {
    public NotMatchPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
