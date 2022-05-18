package com.example.server.businessLayer.Publisher.WebSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/notification");
        //when a client sends a message back to the server during the sws 'socket'.
        registry.setApplicationDestinationPrefixes("/swns");
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //notifications will be the place where the client will read the messages.
        //the communication will be using the stomp protocol.
        registry.addEndpoint("/notifications")
                .setAllowedOrigins("http://localhost:4200", "http://127.0.0.1:4200")
                .withSockJS();
    }
}