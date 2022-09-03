package com.pjt.globalmarket.user.controller;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.user.domain.NeedLogin;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.dto.LoginDto;
import com.pjt.globalmarket.user.dto.SignUpDto;
import com.pjt.globalmarket.user.dto.UserUpdateDto;
import com.pjt.globalmarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
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

    @GetMapping(path = "/{email}/id")
    public boolean checkDuplicatedEmail(@PathVariable(name = "email") String email) {
        return userService.getActiveUserByEmailAndProvider(email, DEFAULT_PROVIDER).isPresent();
    }

    @NeedLogin
    @PostMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void updateUser(@AuthenticationPrincipal UserAuthDetails loginUser, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        if(encoder.matches(userUpdateDto.getPassword(), user.getPassword())) {
            userService.updateUser(user, userUpdateDto.getName(), userUpdateDto.getPhone());
        } else {
            throw new IllegalArgumentException("비밀번호를 잘못 입력했습니다.");
        }
    }

    @NeedLogin
    @PostMapping(path = "/withdrawal")
    public void withdrawalUser(@AuthenticationPrincipal UserAuthDetails loginUser) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        userService.deleteUser(user);
    }
}
