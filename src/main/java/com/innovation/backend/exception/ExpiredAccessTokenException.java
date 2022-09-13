package com.innovation.backend.exception;

public class ExpiredAccessTokenException extends RuntimeException {
    public ExpiredAccessTokenException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
