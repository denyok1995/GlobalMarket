package com.pjt.coupang.user.controller;

import com.pjt.coupang.user.dto.UserDto;
import com.pjt.coupang.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/sign-up")
    public void signUp(@RequestBody UserDto userDto) {
        userService.saveUser(userDto.getEmail(), userDto.getPassword(), userDto.getName(), userDto.getPhone());
    }

    @GetMapping(path = "/check/id")
    public boolean checkDuplicatedEmail(String email) {
        return userService.getUserByEmail(email);
    }
}
