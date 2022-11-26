package com.pjt.globalmarket.webflux;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

import java.util.List;

public interface ChatRepository extends ReactiveMongoRepository<Chat, String>, ChatRepositoryIf {

    @Tailable
    Flux<Chat> findChatsBySenderAndReceiver(String sender, String receiver);

}
