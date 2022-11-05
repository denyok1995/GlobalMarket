package com.pjt.globalmarket.chatting.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjt.globalmarket.chatting.dto.ChattingSubscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
// 1:N (서버:클라이언트)를 Handle 하기 위한 Chatting Handler
public class ChattingHandler extends TextWebSocketHandler { // Text 기반의 Handler 사용

    private final ApplicationEventPublisher eventPublisher;

    private Map<String, WebSocketSession> sessionMap = new HashMap<>();

    private ObjectMapper mapper = new ObjectMapper();

    //protected Class<T> subscriberClass;


    // Message를 받았을 때 동작(호출)
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //super.handleTextMessage(session, message);
        log.info("Websocket Session : {}, Message : {}", session, message.getPayload()); // payload = 전송되는 데이터 여기서는 message 전문이 될 것이다.
        long start = System.currentTimeMillis();
        log.info("Start : {}", start);
        String to = findTo(Objects.requireNonNull(session.getUri()));
        WebSocketSession sessionOfTo = userMap.get(to);
        sessionOfTo.sendMessage(message);

        //DB 접근이 없는 경우 5ms 이내에 동작이 끝이난다.
        ChattingEvent event = ChattingEvent.builder()
                .fromUser(findFrom(Objects.requireNonNull(session.getUri())))
                .toUser(to)
                .payload(message.getPayload())
                .build();
        log.info("Event Thread : {}", Thread.currentThread());
        eventPublisher.publishEvent(event);
        long end = System.currentTimeMillis();
        log.info("End : {}", end);
        log.info("걸린 시간 : {}", end-start);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("Client 접속 성공 : {}", session);
        sessionMap.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        log.info("Client 접속 해제 : {}", session);
        sessionMap.remove(session.getId());
    }
}

