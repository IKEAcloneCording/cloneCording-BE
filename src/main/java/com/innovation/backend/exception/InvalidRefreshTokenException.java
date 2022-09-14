package com.innovation.backend.exception;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
