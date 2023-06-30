package com.ms.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// @EnableWebSocketMessageBroker annotation을 사용해서 STOMP를 사용할 수 있게 설정한다.
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 엔드포인트 등록, CORS 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*");
    }

    // Message broker를 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트기준 메세지를 받을 때, 경로를 설정
        registry.enableSimpleBroker("/sub");
        // 클라이언트기준 메시지 보낼때 경로 설정
        registry.setApplicationDestinationPrefixes("/pub");
    }

}
