package com.innovation.backend.exception;

public class NeedRefreshTokenException extends RuntimeException {
    public NeedRefreshTokenException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
