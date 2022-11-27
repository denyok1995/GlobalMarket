package com.pjt.globalmarket.webflux;

import com.pjt.globalmarket.chatting.dao.ChannelRepository;
import com.pjt.globalmarket.chatting.domain.Channel;
import com.pjt.globalmarket.user.dao.UserRepository;
import com.pjt.globalmarket.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatService {
    
    private final ChatRepository chatRepository;

    public Mono<Chat> saveChat(Chat chat) {
        return chatRepository.save(chat);
    }

    public Flux<Chat> getOneByOneChat(String sender, String receiver) {
        return chatRepository.findChatsBySenderAndReceiver(sender, receiver);
    }

    public Flux<Chat> getRecordOneByOneChat(String sender, String receiver) {
        return chatRepository.findChatsByTwoPeople(sender, receiver);
    }

    public Flux<Chat> getChannelChat(long channelId) {
        return chatRepository.findChatsByChannelIdAndIsChannel(channelId, true);
    }
}
