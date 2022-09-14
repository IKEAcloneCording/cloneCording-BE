package com.innovation.backend.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
