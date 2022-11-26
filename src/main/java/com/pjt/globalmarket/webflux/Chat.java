package com.pjt.globalmarket.webflux;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import java.time.ZonedDateTime;

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

}
