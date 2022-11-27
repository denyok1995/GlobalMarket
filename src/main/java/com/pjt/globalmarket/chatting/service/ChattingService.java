package com.pjt.globalmarket.chatting.service;

import com.pjt.globalmarket.chatting.dao.ChannelRepository;
import com.pjt.globalmarket.chatting.dao.ChattingRepository;
import com.pjt.globalmarket.chatting.domain.Channel;
import com.pjt.globalmarket.chatting.domain.Chatting;
import com.pjt.globalmarket.chatting.handler.ChattingEvent;
import com.pjt.globalmarket.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChattingService {

    private final ChattingRepository chattingRepository;
    private final ChannelRepository channelRepository;

    @EventListener
    @Async
    public void saveChat(ChattingEvent event) {
        log.info("Save Thread : {}", Thread.currentThread());
        log.info("Event 수신 : {}", event);
        chattingRepository.save(Chatting.toEntity(event));
    }

    public List<Chatting> getChat(String fromUser, String toUser) {
        List<Chatting> chat = chattingRepository.findAllByFromUserAndToUser(fromUser, toUser);
        chat.addAll(chattingRepository.findAllByFromUserAndToUser(toUser, fromUser));
        return chat;
    }

    public Channel saveChannel(String name, User opener, List<User> users) {
        Channel channel = Channel.builder()
                .name(name)
                .opener(opener)
                .users(users)
                .build();
        return channelRepository.save(channel);
    }

    public List<Channel> getChannelListByUser(User user) {
        return channelRepository.findAllByUsersContains(user);
    }

    public Channel getChannelById(long channelId) {
        return channelRepository.findById(channelId).orElseThrow();
    }

    public boolean isChannelMember(long channelId, User user) {
        Channel channel = channelRepository.findChannelByIdAndUsersContains(channelId, user);
        return channel != null;
    }


}
