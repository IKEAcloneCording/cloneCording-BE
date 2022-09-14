package com.innovation.backend.exception;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
