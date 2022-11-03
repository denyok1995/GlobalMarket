package com.pjt.globalmarket.chatting.domain;

import com.pjt.globalmarket.chatting.handler.ChattingEvent;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Getter
@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Chatting {

    @Id
    @GeneratedValue
    private long id;

    private String fromUser;

    private String toUser;

    private String payload;

    @Builder.Default
    private ZonedDateTime chatTime = ZonedDateTime.now();

    public static Chatting toEntity(ChattingEvent event) {
        return Chatting.builder()
                .fromUser(event.getFromUser())
                .toUser(event.getToUser())
                .payload(event.getPayload())
                .build();
    }
}
