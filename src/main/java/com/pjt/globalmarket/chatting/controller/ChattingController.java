package com.pjt.globalmarket.chatting.controller;

import com.pjt.globalmarket.chatting.dto.ChannelDto;
import com.pjt.globalmarket.chatting.dto.ChannelInfo;
import com.pjt.globalmarket.chatting.dto.ChattingMessageDto;
import com.pjt.globalmarket.chatting.service.ChattingService;
import com.pjt.globalmarket.common.annotation.NeedLogin;
import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ChattingController {

    private final ChattingService chattingService;
    private final UserService userService;

    @NeedLogin
    @GetMapping(path = "/chatting")
    public List<ChattingMessageDto> getMessages(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                                                @RequestParam String toUser) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        return chattingService.getChat(user.getEmail(), toUser).stream().map(ChattingMessageDto::toDto).collect(Collectors.toList());
    }

    @NeedLogin
    @PostMapping(path = "/chatting/channel")
    public ChannelDto saveChannel(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                                  @RequestBody ChannelInfo channelInfo) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        List<User> users = new ArrayList<>();
        for(long userId : channelInfo.getUserIds()) {
            users.add(userService.getUserById(userId));
        }
        users.add(user);
        return ChannelDto.toDto(chattingService.saveChannel(channelInfo.getName(), user, users));
    }

    @NeedLogin
    @GetMapping(path = "/chatting/channel")
    public List<ChannelDto> getChannelList(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        return chattingService.getChannelListByUser(user).stream().map(ChannelDto::toDto).collect(Collectors.toList());
    }


    //?????? ????????? ???????????? Dto ???????????????
    @NeedLogin
    @GetMapping(path = "/chatting/channel/{channelId}")
    public ChannelDto getChannel(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                                 @PathVariable long channelId) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        boolean isMember = chattingService.isChannelMember(channelId, user);
        if(!isMember) {
            throw new IllegalArgumentException("????????? ???????????? ?????? ???????????????.");
        }

        return ChannelDto.toDto(chattingService.getChannelById(channelId));
    }
}
