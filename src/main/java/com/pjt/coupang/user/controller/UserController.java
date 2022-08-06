package com.pjt.coupang.user.controller;

import com.pjt.coupang.user.dto.UserDto;
import com.pjt.coupang.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;


    @PostMapping(path = "/sign-up")
    public void signUp(@Valid @RequestBody UserDto userDto) {
        userService.saveUser(userDto.getEmail(), encoder.encode(userDto.getPassword()), userDto.getName(), userDto.getPhone());
    }

    @GetMapping(path = "/check/id")
    public boolean checkDuplicatedEmail(String email) {
        return userService.getUserByEmail(email);
    }
}
