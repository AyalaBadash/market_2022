package com.example.server.businessLayer.Publisher.WebSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Service
public class NotificationsController {

    private final NotificationHandler handler;
    @Autowired
    public NotificationsController(NotificationHandler handler) {
        this.handler=handler;

    }
    //registers the user to the dispacher
    @MessageMapping("/start/{name}")
    public void start(StompHeaderAccessor stompHeaderAccessor, @DestinationVariable String name) {
        handler.add(name,stompHeaderAccessor.getSessionId());
    }
    @MessageMapping("/stop/{name}")
    public void stop(StompHeaderAccessor stompHeaderAccessor, @DestinationVariable String name) {
        handler.remove(name,stompHeaderAccessor.getSessionId());
    }


    //Event listener for connections suddenly closed.
    @EventListener
    public void sessionDisconnectionHandler(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        handler.removeErr(sessionId);
    }

}