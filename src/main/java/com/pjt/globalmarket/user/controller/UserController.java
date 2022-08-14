package com.pjt.globalmarket.user.controller;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.user.dto.LoginDto;
import com.pjt.globalmarket.user.dto.SignUpDto;
import com.pjt.globalmarket.user.dto.UserUpdateDto;
import com.pjt.globalmarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.pjt.globalmarket.user.domain.UserConstant.DEFAULT_PROVIDER;

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
    public void signUp(@Valid @RequestBody SignUpDto signUpDto) {
        userService.saveUser(signUpDto.getEmail(), encoder.encode(signUpDto.getPassword()), signUpDto.getName(), signUpDto.getPhone());
    }

    @GetMapping(path = "/check/id")
    public boolean checkDuplicatedEmail(String email) {
        return userService.getActiveUserByEmailAndProvider(email, DEFAULT_PROVIDER).isPresent();
    }

    @PostMapping(path = "/auth/update")
    public void updateUser(@AuthenticationPrincipal UserAuthDetails loginUser, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        String email = loginUser.getUsername();
        userService.updateUser(email, userUpdateDto.getPassword(), userUpdateDto.getName(), userUpdateDto.getPhone());
    }

    @PostMapping(path = "/auth/withdrawal")
    public void withdrawalUser(@AuthenticationPrincipal UserAuthDetails loginUser) {
        userService.deleteUser(loginUser.getUsername(), loginUser.getPassword(), loginUser.getProvider());
    }
}
