package com.codingchosun.backend.exception.emptyrequest;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class EmptyRequestException extends ApiException {
    public EmptyRequestException(ErrorCode errorCode) {
        super(errorCode);
    }
}
