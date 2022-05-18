package com.example.server.businessLayer.Publisher.WebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationsController {

    private final NotificationDispatcher dispatcher;
    @Autowired
    public NotificationsController(NotificationDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
    //registers the user to the dispacher
    @MessageMapping("/start")
    public void start(@Payload Message message, StompHeaderAccessor stompHeaderAccessor) {
        dispatcher.add(message.getFrom(),stompHeaderAccessor.getSessionId());
    }
    @MessageMapping("/stop")
    public void stop(StompHeaderAccessor stompHeaderAccessor) {
        dispatcher.remove(stompHeaderAccessor.getSessionId());
    }
}