package com.pjt.globalmarket.webflux;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepositoryIf{

    private final String SENDER = "sender";
    private final String RECEIVER = "receiver";

    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Flux<Chat> findChatsByTwoPeople(String sender, String receiver) {
        Criteria meSend = new Criteria();
        Criteria youSend = new Criteria();
        Criteria bothSend = new Criteria();

        meSend.andOperator(Criteria.where(SENDER).is(sender), Criteria.where(RECEIVER).is(receiver));
        youSend.andOperator(Criteria.where(SENDER).is(receiver), Criteria.where(RECEIVER).is(sender));
        bothSend.orOperator(meSend, youSend);

        Query query = new Query(bothSend);
        return mongoTemplate.find(query, Chat.class);
    }
}
