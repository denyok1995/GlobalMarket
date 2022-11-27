package com.pjt.globalmarket.chatting.dao;

import com.pjt.globalmarket.chatting.domain.Channel;
import com.pjt.globalmarket.user.domain.User;
import com.pjt.globalmarket.webflux.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

    List<Channel> findAllByUsersContains(User user);

    Channel findChannelByIdAndUsersContains(long id, User user);

}
