package com.pjt.globalmarket.chatting.dto;

import com.pjt.globalmarket.chatting.domain.Channel;
import lombok.*;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelDto {

    private long id;

    private String name;

    private long channelNum;

    private ZonedDateTime createdAt;

    public static ChannelDto toDto(Channel channel) {
        return ChannelDto.builder()
                .id(channel.getId())
                .name(channel.getName())
                .channelNum(channel.getUsers().size())
                .createdAt(channel.getCreatedAt())
                .build();
    }
}
