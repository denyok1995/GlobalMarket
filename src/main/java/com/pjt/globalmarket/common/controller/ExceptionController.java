package com.pjt.globalmarket.common.controller;

import com.pjt.globalmarket.common.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ErrorResponseDto authExceptionHandler() {
        return returnErrorDto(HttpStatus.FORBIDDEN, "Need To Login!!");
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ErrorResponseDto loginExceptionHandler() {
        return returnErrorDto(HttpStatus.FORBIDDEN, "Need To Sign-Up!!");
    }

    @ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class})
    public ErrorResponseDto orElseThrowHandler() {
        return returnErrorDto(HttpStatus.INTERNAL_SERVER_ERROR, "Wrong Input!!");
    }

    private ErrorResponseDto returnErrorDto(HttpStatus status, String message) {
        return ErrorResponseDto.builder()
                .httpStatus(status)
                .message(message)
                .build();
    }
}
