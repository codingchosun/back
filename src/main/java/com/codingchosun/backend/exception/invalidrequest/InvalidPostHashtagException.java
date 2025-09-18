package com.codingchosun.backend.exception.invalidrequest;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class InvalidPostHashtagException extends ApiException {
    public InvalidPostHashtagException(ErrorCode errorCode) {
        super(errorCode);
    }
}
