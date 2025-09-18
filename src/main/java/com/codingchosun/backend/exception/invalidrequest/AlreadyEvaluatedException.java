package com.codingchosun.backend.exception.invalidrequest;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class AlreadyEvaluatedException extends ApiException {
    public AlreadyEvaluatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
