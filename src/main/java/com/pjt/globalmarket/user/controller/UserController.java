package com.pjt.globalmarket.user.controller;

import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.user.domain.NeedLogin;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.dto.LoginDto;
import com.pjt.globalmarket.user.dto.SignUpDto;
import com.pjt.globalmarket.user.dto.UserCreateInfo;
import com.pjt.globalmarket.user.dto.UserUpdateDto;
import com.pjt.globalmarket.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

import static com.pjt.globalmarket.user.domain.UserConstant.DEFAULT_PROVIDER;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;


    @PostMapping(path = "/sign-in")
    @ApiOperation(value = "로그인", notes = "회원가입을 진행했는지 확인한다.")
    public void signIn(LoginDto loginDto) {

    }

    @GetMapping(path = "/sign-out")
    @ApiOperation(value = "로그아웃", notes = "로그인 한 회원의 세션을 끊는다.")
    public void signOut() {

    }

    @PostMapping(path = "/sign-up")
    @ApiOperation(value = "회원가입", notes = "유저 정보를 저장한다.")
    public UserCreateInfo signUp(@Valid @RequestBody SignUpDto signUpDto) {
        return UserCreateInfo.toDto(userService.saveUser(signUpDto.getEmail()
                , encoder.encode(signUpDto.getPassword()), signUpDto.getName(), signUpDto.getPhone()));
    }

    @GetMapping(path = "/{email}/id")
    @ApiOperation(value = "사용자 이메일 중복확인", notes = "동일한 이메일이 존재하는지 조회한다.")
    public boolean checkDuplicatedEmail(@PathVariable(name = "email") String email) {
        return userService.getActiveUserByEmailAndProvider(email, DEFAULT_PROVIDER).isPresent();
    }

    @NeedLogin
    @PutMapping
    @ApiOperation(value = "회원정보 수정", notes = "유저 정보를 수정한다.")
    public UserCreateInfo updateUser(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser
            , @Valid @RequestBody UserUpdateDto userUpdateDto) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        if(encoder.matches(userUpdateDto.getPassword(), user.getPassword())) {
            return UserCreateInfo.toDto(userService.updateUser(user, userUpdateDto.getName(), userUpdateDto.getPhone()));
        } else {
            throw new IllegalArgumentException("비밀번호를 잘못 입력했습니다.");
        }
    }

    @NeedLogin
    @DeleteMapping
    @ApiOperation(value = "회원 탈퇴", notes = "유저 정보를 삭제한다.")
    public void withdrawalUser(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        userService.deleteUser(user);
    }
}
