package com.pjt.globalmarket.webflux;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Document(collection = "Chat")
public class Chat {

    @Id
    private String id;

    private String sender;

    private String receiver;

    private String content;

    private Boolean isChannel;

    private long channelId;

    @CreatedDate
    private LocalDateTime zonedDateTime;

}
