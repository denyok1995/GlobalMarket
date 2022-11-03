package com.pjt.globalmarket.chatting.service;

import com.pjt.globalmarket.chatting.dao.ChattingRepository;
import com.pjt.globalmarket.chatting.domain.Chatting;
import com.pjt.globalmarket.chatting.handler.ChattingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChattingService {

    private final ChattingRepository chattingRepository;

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
}
