package com.pjt.globalmarket.chatting.dto;

import com.pjt.globalmarket.webflux.Chat;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelMessageDto {

    private String sender;

    private String message;

    public static ChannelMessageDto toDto(Chat chat) {
        return ChannelMessageDto.builder()
                .sender(chat.getSender())
                .message(chat.getContent())
                .build();
    }
}
