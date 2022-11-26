package com.pjt.globalmarket.webflux;

import com.pjt.globalmarket.common.annotation.NeedLogin;
import com.pjt.globalmarket.config.auth.UserAuthDetails;
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

    private final ChatRepository chatRepository;

    //@NeedLogin
    @GetMapping(value = "/chat/record/{receiver}")
    public Flux<Chat> getChatFlux(//@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                                  @PathVariable String receiver) {
        //String sender = loginUser.getUsername();
        String sender = "sa";
        return chatRepository.findChatsByTwoPeople(receiver, sender);
    }

    //@NeedLogin
    @GetMapping(value = "/chat/{receiver}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> getChat(//@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                              @PathVariable String receiver) {
        //String sender = loginUser.getUsername();
        String sender = "sa";
        return chatRepository.findChatsBySenderAndReceiver(sender, receiver);
    }

    //@NeedLogin
    @PostMapping(value = "/chat/{receiver}")
    public Mono<Chat> saveChat(//@AuthenticationPrincipal @ApiIgnore UserAuthDetails loginUser,
                               @PathVariable String receiver,
                               @RequestBody String content) {
        Chat chat = Chat.builder()
                //.sender(loginUser.getUsername())
                .sender("sa")
                .receiver(receiver)
                .content(content)
                .build();
        return chatRepository.save(chat);
    }
}
