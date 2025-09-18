package com.codingchosun.backend.exception.invalidtime;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class InvalidTimeSetupException extends ApiException {
    public InvalidTimeSetupException(ErrorCode errorCode) {
        super(errorCode);
    }
}
