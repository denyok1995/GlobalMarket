package com.pjt.globalmarket.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuthController {

    @GetMapping(path = "/loginForm")
    public String oauthLoginForm() {
        return "loginForm";
    }
}
