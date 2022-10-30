package com.pjt.globalmarket.chatting.dao;

import com.pjt.globalmarket.chatting.domain.Chatting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingRepository extends JpaRepository<Chatting, Long> {
}
