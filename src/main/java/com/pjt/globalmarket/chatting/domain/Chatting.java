package com.pjt.globalmarket.chatting.domain;

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
}
