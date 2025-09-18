package com.codingchosun.backend.exception.file;

import com.codingchosun.backend.exception.common.ApiException;
import com.codingchosun.backend.exception.common.ErrorCode;

public class FileStorageFailedException extends ApiException {
    public FileStorageFailedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
