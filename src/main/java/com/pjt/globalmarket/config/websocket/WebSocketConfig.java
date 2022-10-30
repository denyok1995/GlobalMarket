package com.pjt.globalmarket.config.websocket;

import com.pjt.globalmarket.chatting.handler.ChattingHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSocket // websocket 활성화
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChattingHandler chattingHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chattingHandler, "/chat/**") // handler 추가와 end point 설정 (ws://localhost/chat)
                .setAllowedOrigins("*"); // 여러 도메인에서 요청할 수 있게 CORS 처리 추가
    }
}
