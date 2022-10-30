package com.pjt.globalmarket.chatting.dto;

import com.pjt.globalmarket.chatting.domain.Chatting;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChattingMessageDto {

    private long id;

    private String message;

    private String fromUser;

    private String toUser;

    private ZonedDateTime chatTime;

    public static ChattingMessageDto toDto(Chatting chatting) {
        return ChattingMessageDto.builder()
                .id(chatting.getId())
                .message(chatting.getPayload())
                .fromUser(chatting.getFromUser())
                .toUser(chatting.getToUser())
                .chatTime(chatting.getChatTime())
                .build();
    }
}
