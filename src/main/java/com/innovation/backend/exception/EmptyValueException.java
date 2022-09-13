package com.innovation.backend.exception;

public class EmptyValueException extends RuntimeException {
    public EmptyValueException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
