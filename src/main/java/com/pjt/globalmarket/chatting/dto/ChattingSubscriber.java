package com.pjt.globalmarket.chatting.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@JsonIgnoreProperties
public class ChattingSubscriber {

    private String fromUser;

    private String toUser;

    private String sessionId;

    private String content;

}
