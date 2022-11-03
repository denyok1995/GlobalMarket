package com.pjt.globalmarket.chatting.handler;

import lombok.*;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@ToString
public class ChattingEvent {

    private String fromUser;

    private String toUser;

    private String payload;
}
