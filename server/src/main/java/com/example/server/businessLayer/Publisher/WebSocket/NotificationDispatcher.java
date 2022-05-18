package com.example.server.businessLayer.Publisher.WebSocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.*;


@EnableScheduling
public class NotificationDispatcher {
    private SimpMessagingTemplate template;
    //Set of the username-messages;
    private HashMap<String, List<Message>> messages=new HashMap<>();
    //set of usename-sessionid
    private HashMap<String,String> listeners = new HashMap<>();

    public NotificationDispatcher(SimpMessagingTemplate template) {

        this.template = template;
    }

    @Scheduled(fixedDelay = 2000)
    public void dispatch() {
        for (Map.Entry<String, List<Message>> messagesSet : messages.entrySet()) {
            try {
                for (Message mess : messagesSet.getValue()) {

                    String sessionId = listeners.get(messagesSet.getKey());
                    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
                    headerAccessor.setSessionId(sessionId);
                    headerAccessor.setLeaveMutable(true);
                    template.convertAndSendToUser(
                            sessionId,
                            "/notification/item",
                            mess,
                            headerAccessor.getMessageHeaders());
                }

                //clear the notifications sent.
                messages.get(messagesSet.getKey()).clear();
            }
            catch (Exception e){}

        }
    }
    @EventListener
    public void sessionDisconnectionHandler(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        remove(sessionId);
    }


    //TODO: edit
    public void remove(String sessionId) {

        String name ="";
        for (Map.Entry<String, String> list : listeners.entrySet()){
            if(list.getValue().equals(sessionId)){
                name=list.getValue();
            }
        }
        if(!name.isEmpty()){
            listeners.remove(name);
            messages.remove(name);
        }
    }


    //TODO: edit
    public void add(String username, String sessionId) {
        messages.put(username,new ArrayList<>());
        listeners.put(username, sessionId);
    }
}
