package com.codingchosun.backend.exception.invalidrequest;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class NotPostImageException extends ApiException {
    public NotPostImageException(ErrorCode errorCode) {
        super(errorCode);
    }
}
