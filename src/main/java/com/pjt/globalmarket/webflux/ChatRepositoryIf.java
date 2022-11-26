package com.pjt.globalmarket.webflux;

import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

public interface ChatRepositoryIf {

    Flux<Chat> findChatsByTwoPeople(String sender, String receiver);
}
