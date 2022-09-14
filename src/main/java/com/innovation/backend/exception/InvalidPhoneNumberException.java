package com.innovation.backend.exception;

public class InvalidPhoneNumberException extends RuntimeException {
    public InvalidPhoneNumberException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
