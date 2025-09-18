package com.codingchosun.backend.exception.invalidrequest;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class UnauthorizedActionException extends ApiException {
    public UnauthorizedActionException(ErrorCode errorCode) {
        super(errorCode);
    }
}
