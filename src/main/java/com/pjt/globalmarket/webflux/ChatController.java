package com.pjt.globalmarket.webflux;

import com.pjt.globalmarket.chatting.service.ChattingService;
import com.pjt.globalmarket.common.annotation.NeedLogin;
import com.pjt.globalmarket.config.auth.UserAuthDetails;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChattingService chattingService;
    private final UserService userService;

    //@NeedLogin
    @GetMapping(path = "/chat/record/{receiver}")
    public Flux<Chat> getChat(//@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                                  @PathVariable String receiver) {
        //String sender = loginUser.getUsername();
        String sender = "sa";
        return chatService.getRecordOneByOneChat(receiver, sender);
    }

    //@NeedLogin
    @GetMapping(path = "/chat/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getChatFlux(//@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                              @PathVariable String receiver) {
        //String sender = loginUser.getUsername();
        String sender = "sa";
        return chatService.getOneByOneChat(sender, receiver);
    }

    @NeedLogin
    @PostMapping(path = "/chat/{receiver}")
    public Mono<Chat> saveChat(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                               @PathVariable String receiver,
                               @RequestBody String content) {
        Chat chat = Chat.builder()
                //.sender(loginUser.getUsername())
                .sender("sa")
                .receiver(receiver)
                .content(content)
                .build();
        return chatService.saveChat(chat);
    }

    @NeedLogin
    @PostMapping(path = "/chat/channel/{channelId}")
    public Mono<Chat> saveChat(@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                               @PathVariable long channelId,
                               @RequestBody String content) {
        User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        boolean isMember = chattingService.isChannelMember(channelId, user);
        if(!isMember) {
            throw new IllegalArgumentException("채널에 해당되지 않은 유저입니다.");
        }

        Chat chat = Chat.builder()
                .sender(loginUser.getUsername())
                .channelId(channelId)
                .isChannel(true)
                .content(content)
                .build();
        return chatService.saveChat(chat);
    }

    //@NeedLogin
    @GetMapping(path = "/chat/channel/{channelId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getChannelChatFlux(//@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                              @PathVariable long channelId) {
        /*User user = userService.getActiveUserByEmailAndProvider(loginUser.getUsername(), loginUser.getProvider()).orElseThrow();
        boolean isMember = chattingService.isChannelMember(channelId, user);
        if(!isMember) {
            throw new IllegalArgumentException("채널에 해당되지 않은 유저입니다.");
        }*/

        return chatService.getChannelChat(channelId);
    }
}
