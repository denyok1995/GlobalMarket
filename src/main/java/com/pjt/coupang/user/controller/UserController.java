package com.pjt.coupang.user.controller;

import com.pjt.coupang.user.domain.User;
import com.pjt.coupang.user.dto.LoginDto;
import com.pjt.coupang.user.dto.UserDto;
import com.pjt.coupang.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private static final String LOGIN_ID = "loginId";

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/sign-up")
    public void signUp(@Valid @RequestBody UserDto userDto) {
        userService.saveUser(userDto.getEmail(), userDto.getPassword(), userDto.getName(), userDto.getPhone());
    }

    @GetMapping(path = "/check/id")
    public boolean checkDuplicatedEmail(String email) {
        return userService.getUserByEmail(email);
    }

    @PostMapping(path = "/sign-in")
    public void logIn(@RequestBody LoginDto loginDto,
                      HttpServletRequest request) {
        Optional<User> user = userService.login(loginDto.getEmail(), loginDto.getPassword());
        if(user.isEmpty()) {
            throw new IllegalArgumentException("아이디가 존재하지 않습니다.");
        }

        HttpSession httpSession = request.getSession();
        httpSession.setAttribute(LOGIN_ID, user.get().getEmail());
    }

    @PostMapping(path = "/sign-out")
    public void logOut(HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession(false);
        if(httpSession != null) {
            httpSession.invalidate();
        }
    }

}
