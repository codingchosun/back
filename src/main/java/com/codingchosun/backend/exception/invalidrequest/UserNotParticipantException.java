package com.codingchosun.backend.exception.invalidrequest;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class UserNotParticipantException extends ApiException {
    public UserNotParticipantException(ErrorCode errorCode) {
        super(errorCode);
    }
}
