package com.pjt.globalmarket.user.controller;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.user.dto.LoginDto;
import com.pjt.globalmarket.user.dto.UserDto;
import com.pjt.globalmarket.user.dto.UserUpdateDto;
import com.pjt.globalmarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    @PostMapping(path = "/sign-in")
    public void signIn(LoginDto loginDto) {

    }

    @GetMapping(path = "/sign-out")
    public void signOut() {

    }

    @PostMapping(path = "/sign-up")
    public void signUp(@Valid @RequestBody UserDto userDto) {
        userService.saveUser(userDto.getEmail(), encoder.encode(userDto.getPassword()), userDto.getName(), userDto.getPhone());
    }

    @GetMapping(path = "/check/id")
    public boolean checkDuplicatedEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping(path = "/auth/update")
    public void updateUser(@AuthenticationPrincipal UserAuthDetails loginUser, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        String email = loginUser.getUsername();
        userService.updateUser(email, userUpdateDto.getPassword(), userUpdateDto.getName(), userUpdateDto.getPhone());
    }
}
