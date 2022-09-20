package com.pjt.globalmarket.common.controller;

import com.pjt.globalmarket.common.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler({AuthenticationCredentialsNotFoundException.class, InternalAuthenticationServiceException.class})
    public ResponseEntity<ErrorResponseDto> authExceptionHandler() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(returnErrorDto(HttpStatus.FORBIDDEN, "Need To Login!!"));
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> loginExceptionHandler() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(returnErrorDto(HttpStatus.FORBIDDEN, "Need To Sign-Up!!"));
    }

    @ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponseDto> orElseThrowHandler() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(returnErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, "Wrong Input!!"));
    }

    private ErrorResponseDto returnErrorDto(HttpStatus status, String message) {
        return ErrorResponseDto.builder()
                .httpStatus(status)
                .message(message)
                .build();
    }
}
