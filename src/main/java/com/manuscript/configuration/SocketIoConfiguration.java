package com.manuscript.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import static com.manuscript.configuration.WebSocketConstant.MAIN_END_POINT;
import static com.manuscript.configuration.WebSocketConstant.TOPIC;


@Configuration
@EnableWebSocketMessageBroker
public class SocketIoConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(TOPIC);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(MAIN_END_POINT).setAllowedOriginPatterns("*").withSockJS();
    }
}
