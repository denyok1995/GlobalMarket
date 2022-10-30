package com.pjt.globalmarket.chatting.handler;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.*;

@Component
@Slf4j
// 1:N (서버:클라이언트)를 Handle 하기 위한 Chatting Handler
public class ChattingHandler extends TextWebSocketHandler { // Text 기반의 Handler 사용
    private Map<String, WebSocketSession> userMap = new HashMap<>();


    // Message를 받았을 때 동작(호출)
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        //super.handleTextMessage(session, message);
        log.info("Websocket Session : {}, Message : {}", session, message.getPayload()); // payload = 전송되는 데이터 여기서는 message 전문이 될 것이다.

        String to = findTo(Objects.requireNonNull(session.getUri()));
        WebSocketSession sessionOfTo = userMap.get(to);
        sessionOfTo.sendMessage(message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String username = findFrom(Objects.requireNonNull(session.getUri()));
        log.info("{} Client 접속 성공 : {}", username, session);
        userMap.put(username, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        String from = findFrom(Objects.requireNonNull(session.getUri()));
        log.info("{} Client 접속 해제 : {}",from, session);
        userMap.remove(from);
    }

    private String findTo(URI uri) {
        String[] parsedUri = uri.getPath().split("/");
        return parsedUri[parsedUri.length-1];
    }

    private String findFrom(URI uri) {
        String[] parsedUri = uri.getPath().split("/");
        return parsedUri[parsedUri.length-2];
    }
}
