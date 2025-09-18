package com.codingchosun.backend.exception.invalidrequest;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class MissingValueException extends ApiException {
    public MissingValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
