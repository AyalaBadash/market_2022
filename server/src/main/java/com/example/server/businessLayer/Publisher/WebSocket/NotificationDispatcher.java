package com.example.server.businessLayer.Publisher.WebSocket;

import com.example.server.serviceLayer.Notifications.Notification;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.*;


@Service
@EnableScheduling
public class NotificationDispatcher {
    private SimpMessagingTemplate template;


    //Set of the sessionis-messages;
    private HashMap<String, List<Notification>> messages=new HashMap<>();

    public NotificationDispatcher(SimpMessagingTemplate template) {

        this.template = template;
    }

    //For each listener, send all real time notifications available.
    @Scheduled(fixedDelay = 2000)
    public void dispatch() {
        for (Map.Entry<String, List<Notification>> messagesSet : messages.entrySet()) {
            try {
                for (Notification mess : messagesSet.getValue()) {

                    String sessionId = messagesSet.getKey();
                    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
                    headerAccessor.setSessionId(sessionId);
                    headerAccessor.setLeaveMutable(true);
                    template.convertAndSendToUser(
                            sessionId,
                            "/notification/item",
                            mess.getMessage(),
                            headerAccessor.getMessageHeaders()) ;
                }

                //clear the notifications sent.
                messages.get(messagesSet.getKey()).clear();
            }
            catch (Exception e){}

        }
    }

    //removes a session by session.
    public List<Notification> remove(String sessionId) {

        if (!messages.containsKey(sessionId)) {
           return new ArrayList<>();
        }
        List<Notification> nots= messages.get(sessionId);
        messages.remove(sessionId);
        return nots;
    }


    //Add new session to the map.
    public boolean add( String sessionId) {

        if(messages.containsKey(sessionId)){
            return false;
        }
        messages.put(sessionId,new ArrayList<>());
        return true;
    }


    //sends message to user who just logged in.
    public boolean addMessgae(String sessionId, Notification notification) {

        if(!messages.containsKey(sessionId)){
            return false;
        }
        messages.get(sessionId).add(notification);
        return true;
    }
}
