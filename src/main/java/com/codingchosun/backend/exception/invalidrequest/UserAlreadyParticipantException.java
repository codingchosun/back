package com.codingchosun.backend.exception.invalidrequest;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class UserAlreadyParticipantException extends ApiException {
    public UserAlreadyParticipantException(ErrorCode errorCode) {
        super(errorCode);
    }
}
