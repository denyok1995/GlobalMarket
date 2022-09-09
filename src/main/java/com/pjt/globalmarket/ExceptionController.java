package com.pjt.globalmarket;

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
    public ResponseEntity<String> authExceptionHandler() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Need To Login!!");
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<String> loginExceptionHandler() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Need To Sign-Up!!");
    }

    @ExceptionHandler({NoSuchElementException.class, IllegalArgumentException.class})
    public ResponseEntity<String> orElseThrowHandler() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Wrong Input!!");
    }
}
