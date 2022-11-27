package com.pjt.globalmarket.chatting.dto;

import com.pjt.globalmarket.user.domain.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ChannelDetails {
    private long id;

    private String name;

    private User opener;

    private List<User> users;

    private ZonedDateTime createdAt;
}
