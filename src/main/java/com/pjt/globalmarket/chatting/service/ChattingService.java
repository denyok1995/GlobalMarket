package com.pjt.globalmarket.chatting.service;

import com.pjt.globalmarket.chatting.dao.ChattingRepository;
import com.pjt.globalmarket.chatting.domain.Chatting;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChattingService {

    private final ChattingRepository chattingRepository;

    public long saveChat(Chatting chat) {
        return chattingRepository.save(chat).getId();
    }

    public List<Chatting> getChat(String fromUser, String toUser) {
        List<Chatting> chat = chattingRepository.findAllByFromUserAndToUser(fromUser, toUser);
        chat.addAll(chattingRepository.findAllByFromUserAndToUser(toUser, fromUser));
        return chat;
    }
}
