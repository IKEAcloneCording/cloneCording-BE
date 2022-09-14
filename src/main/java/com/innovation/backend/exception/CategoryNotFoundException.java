package com.innovation.backend.exception;

import static com.innovation.backend.exception.ErrorCode.CATEGORY_NOT_FOUND;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
