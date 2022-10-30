package com.pjt.globalmarket.chatting.dao;

import com.pjt.globalmarket.chatting.domain.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ChattingRepository extends JpaRepository<Chatting, Long> {

    List<Chatting> findAllByFromUserAndToUser(String fromUser, String toUser);
}
