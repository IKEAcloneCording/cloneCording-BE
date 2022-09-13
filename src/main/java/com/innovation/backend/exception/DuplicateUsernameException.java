package com.innovation.backend.exception;

public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
