package com.innovation.backend.exception;

public class InvalidAccessTokenException extends RuntimeException {
    public InvalidAccessTokenException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
