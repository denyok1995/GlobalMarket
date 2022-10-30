package com.pjt.globalmarket.chatting.controller;

import com.pjt.globalmarket.chatting.dto.ChattingMessageDto;
import com.pjt.globalmarket.chatting.service.ChattingService;
import com.pjt.globalmarket.common.annotation.NeedLogin;
import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ChattingController {

    private final ChattingService chattingService;
    private final UserService userService;

    @NeedLogin
    @GetMapping(path = "/chat")
    public List<ChattingMessageDto> getMessages(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                                                @RequestParam String toUser) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        return chattingService.getChat(user.getEmail(), toUser).stream().map(ChattingMessageDto::toDto).collect(Collectors.toList());
    }
}
