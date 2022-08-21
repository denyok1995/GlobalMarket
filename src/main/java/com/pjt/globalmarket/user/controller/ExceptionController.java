package com.pjt.globalmarket.user.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@Slf4j
public class ExceptionController {

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public HttpStatus AuthExceptionHandler() {
        log.warn("Login!!!");
        return HttpStatus.FORBIDDEN;
    }
}
