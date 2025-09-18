package com.codingchosun.backend.exception.invalidrequest;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class NotAdminException extends ApiException {
    public NotAdminException(ErrorCode errorCode) {
        super(errorCode);
    }
}
