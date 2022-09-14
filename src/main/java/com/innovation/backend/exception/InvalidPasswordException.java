package com.innovation.backend.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
