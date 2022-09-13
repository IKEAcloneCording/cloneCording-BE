package com.innovation.backend.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
