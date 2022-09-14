package com.innovation.backend.exception;

public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
