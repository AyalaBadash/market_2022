package com.example.server.businessLayer.Publisher;

import com.example.server.serviceLayer.Notifications.DelayedNotifications;
import com.example.server.serviceLayer.Notifications.Notification;
import com.example.server.serviceLayer.Notifications.RealTimeNotifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class NotificationHandler {

    //TODO ADD TEXT DISPATCHER
    //holds notifications to send to each domain(by the member name)
    private Map<String, List<Notification>> delayedMessages;
    private Publisher dispatcher;

    //Map for sessionId-name pairs.
    private Map<String, String> sessions;


    @Autowired
    public NotificationHandler(Publisher dispatcher) {
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

    /**
     * Add new session.
     * @param name name of the new visitor that logged in.
     * @param sessionId the sessionId of the connection.
     * @return
     */
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

    /**
     * Callback method for user logged in.
     * @param name name of the new visitor that logged in.
     * @return
     */
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

    /**
     * Remove a session in case of logout.
     * @param name the name of the visitor to remove.
     * @param sessionId the sessionId of the connection
     * @return
     */
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

    /**
     * Method in case visitor suddenly disconnected.
     * @param sessionId the sessionId to send to.
     * @return
     */
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

    /**
     * The main method for sending a notification for a user.
     * @param name the name of the visitor to send the message
     * @param notification the notification needed to be sent.
     * @param isMember bool field that say if the visitor is a member.
     * @return
     */
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
                if(dispatcher instanceof TextDispatcher){
                    DelayedNotifications not= new DelayedNotifications();
                    not.createMessage("Delayed message: \n"+ notification.getMessage());
                    dispatcher.addMessgae(name,not);
                }
            }
        }
        return true;
    }

    /**
     * Sends to all owner that item is bought from shop.
     * @param buyer the man buyed the items.
     * @param names the names of the owners to send to.
     * @param shopName the shop name
     * @param itemsNames the baught items list.
     * @param prices the bought items prices.
     */
    public void sendItemBaughtNotificationsBatch(String buyer, ArrayList<String> names, String shopName, ArrayList<String> itemsNames, ArrayList<Double> prices) {

        RealTimeNotifications not;
        for (String name : names) {
            for (int i = 0; i < itemsNames.size(); i++) {
                not = new RealTimeNotifications();
                not.createBuyingOfferMessage(buyer, shopName, itemsNames.get(i), prices.get(i));
                sendNotification(name,not,true);
            }
        }
    }

    public void sendShopClosedBatchNotificationsBatch(ArrayList<String> strings, String shopName) {
        RealTimeNotifications not=new RealTimeNotifications();
        not.createShopClosedMessage(shopName);
        for(String name: strings){
            sendNotification(name,not,true);
        }
    }

    public void sendAppointmentRemovedNotification(String firedAppointed, String shopName) {
        RealTimeNotifications not= new RealTimeNotifications();
        not.createShopPermissionDeniedMessage(shopName,firedAppointed);
        sendNotification(firedAppointed,not,true);
    }

    public void setService(Publisher o) {
        dispatcher=o;
    }

    public int getDelayednots(String name){
        return delayedMessages.get(name).size();
    }
}
