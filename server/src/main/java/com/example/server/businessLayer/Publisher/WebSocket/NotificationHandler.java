package com.example.server.businessLayer.Publisher.WebSocket;

import com.example.server.ResourcesObjects.MarketException;
import com.example.server.businessLayer.ExternalComponents.Security;
import com.example.server.serviceLayer.Notifications.DelayedNotifications;
import com.example.server.serviceLayer.Notifications.Notification;
import com.example.server.serviceLayer.Notifications.RealTimeNotifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationHandler {

    //holds notifications to send to each domain(by the member name)
    private Map<String, List<Notification>> delayedMessages;
    private NotificationDispatcher dispatcher;

    //Map for sessionId-name pairs.
    private Map<String, String> sessions;


    @Autowired
    public NotificationHandler(NotificationDispatcher dispatcher) {
        this.dispatcher = dispatcher;
        delayedMessages = new ConcurrentHashMap<>();
        sessions = new ConcurrentHashMap<>();
    }

    //Sends message to user who just logged in.
    public synchronized boolean sendMessageToLogged(String name, Notification notification){
        if(!sessions.containsKey(name)){
            return false;
        }
        String sessionId=sessions.get(name);
        return dispatcher.addMessgae(sessionId,notification);
    }

    //Add new session.
    public synchronized boolean add(String name, String sessionId) {

        if(delayedMessages.containsKey(name)){
            return false;
        }
        sessions.put(name,sessionId);
        delayedMessages.put(name,new ArrayList<>());
        if(dispatcher.add(sessionId)){
            return sendAllDelayedNotifications(name);
        }
        else{
            return false;
        }
    }

    //Callback method for user logged in.
    private boolean sendAllDelayedNotifications(String name) {

        if(!delayedMessages.containsKey(name)){
            return true;
        }
        List<Notification> notifications= delayedMessages.get(name);
        for(Notification not : notifications){
            if(! sendMessageToLogged(name, not)){
                return false;
            }
        }

        delayedMessages.get(name).clear();
        return true;
    }

    //Remove a session in case of logout.
    public synchronized boolean remove(String name, String sessionId) {
        if (!delayedMessages.containsKey(name)) {
            return false;
        }
        List<Notification> nots= dispatcher.remove(sessionId);
        if(!nots.isEmpty()){
            delayedMessages.get(name).addAll(nots);
        }
        sessions.remove(name);
        delayedMessages.remove(name);
        return true;
    }

    //Method in case visitor suddenly disconnected.
    public boolean removeErr(String sessionId) {
        String name="";
        for(Map.Entry<String ,String> set : sessions.entrySet()){
            if(set.getValue().equals(sessionId)){
                name=set.getKey();
            }
        }
        if(name.isEmpty()){
            return false;
        }
        List<Notification> nots= dispatcher.remove(sessionId);
        if(!nots.isEmpty()){
            delayedMessages.get(name).addAll(nots);
        }
        sessions.remove(name);
        delayedMessages.remove(name);
        return true;
    }
    //Check for the market if a memeber/user is logged in .
    public boolean isLogged(String name){
        return sessions.containsKey(name);
    }

    //The main method for sending a notification for a user.
    public boolean sendNotification(String name , Notification notification, boolean isMember){

        if(sessions.containsKey(name)){
            //if user logged.
            String session= sessions.get(name);
            dispatcher.addMessgae(session,notification);
            return true;
        }
        else{
            //if not logged in. save if member
            if(isMember){
                if(!delayedMessages.containsKey(name)){
                    delayedMessages.put(name, new ArrayList<>());
                }
                delayedMessages.get(name).add(notification);
            }
        }
        return true;
    }
}
