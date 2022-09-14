package com.innovation.backend.exception;

import com.innovation.backend.dto.response.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler
    public ResponseEntity<ResponseDto> DuplicateUsernameExceptionHandler(DuplicateUsernameException exception) {
        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.DUPLICATE_EMAIL), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> EmptyValueExceptionHandler(EmptyValueException exception) {
        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.EMPTY_VALUE), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> ExpiredAccessTokenExceptionHandler(ExpiredAccessTokenException exception) {
        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.EXPIRED_ACCESS_TOKEN), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> InvalidAccessTokenExceptionHandler(InvalidAccessTokenException exception) {
        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.INVALID_ACCESS_TOKEN), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> InvalidLoginExceptionHandler(InvalidLoginException exception) {
        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.INVALID_LOGIN), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> InvalidPasswordExceptionHandler(InvalidPasswordException exception) {
        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.INVALID_PASSWORD), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> InvalidPhoneNumberExceptionHandler(InvalidPhoneNumberException exception) {
        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.INVALID_PHONE_NUMBER), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> InvalidRefreshTokenExceptionHandler(InvalidRefreshTokenException exception) {
        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.INVALID_REFRESH_TOKEN), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> InvalidUsernameExceptionHandler(InvalidUsernameException exception) {
        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.INVALID_EMAIL), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> MemberNotFoundExceptionHandler(MemberNotFoundException exception) {
        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.MEMBER_NOT_FOUND), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> NeedRefreshTokenExceptionHandler(NeedRefreshTokenException exception) {
        return new ResponseEntity<>(ResponseDto.fail(ErrorCode.NEED_REFRESH_TOKEN), HttpStatus.UNAUTHORIZED);
    }

}
