package com.example.server.businessLayer.Publisher;

import com.example.server.ResourcesObjects.MarketException;
import com.example.server.businessLayer.ExternalComponents.Security;
import com.example.server.businessLayer.Publisher.WebSocket.Publisher;
import com.example.server.serviceLayer.FacadeObjects.OutputMessage;
import com.example.server.serviceLayer.Notifications.DelayedNotifications;
import com.example.server.serviceLayer.Notifications.RealTimeNotifications;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class AddressBank {

    //singleton to avoid resending notifications and to control all notifications from one instance of one object.
    private static AddressBank instance = null;

    //holds notifications to send to each domain(by the member name)
    private Map<String, List<DelayedNotifications>> membersNotifications;

    //adapt the member name to the name given to the socket when the visitor signed in
    private Map<String, String> memberNameToSocketName;


    //the messages' publisher.
    private Publisher publisher;



    public static synchronized AddressBank getInstance() {

        if (instance == null) {
            instance = new AddressBank();
        }
        return instance;
    }

    private AddressBank() {
        membersNotifications = new ConcurrentHashMap<>();
        memberNameToSocketName = new ConcurrentHashMap<>();
        publisher= new Publisher();
    }

    //add new notification to the list in case member not logged in.
    //the name is the members name.
    public void addNotification(String name, DelayedNotifications not) {

        //get the members name
        if (!membersNotifications.containsKey(name)) {
            membersNotifications.put(name, new ArrayList<>());
        }
        membersNotifications.get(name).add(not);
    }

    //Get all notification of a member by its name.
    public List<DelayedNotifications> getNotifications(String Name) {
        if (!membersNotifications.containsKey(Name)) {
            return new ArrayList<>();
        } else {
            List<DelayedNotifications> ret = membersNotifications.get(Name);
            membersNotifications.remove(Name);
            return ret;
        }
    }

    //Return the socket name of a member.
    public String getAddress(String memberName) throws MarketException {
        if (!memberNameToSocketName.containsKey(memberName)) {
            throw new MarketException("Domain does not exist in system right now.");
        }
        return memberNameToSocketName.get(memberName);
    }

    //check if visitor/member is logged by the socket name.
    public boolean isExists(String user) {
        return memberNameToSocketName.containsKey(user);
    }

    //Add new socket. when guest logged in both values will be the visitor name.
    public void addAddress(String name) {
        if (memberNameToSocketName.containsKey(name)) {
            memberNameToSocketName.remove(name);
        }
        memberNameToSocketName.put(name, name);
    }

    //Remove socket name from list when logged out of the system.
    public void removeAddress(String name) {

        if (memberNameToSocketName.containsKey(name)) {

            memberNameToSocketName.remove(name);
        }

    }

    private void sendImmediateNotification(String name, RealTimeNotifications not) throws MarketException {

        try {

            //get the socket name of a member/visitor.
            String address = getAddress(name);
            //send the notification to the given address.
            //TODO: send to publisher.


        } catch (Exception e) {
            if (e.getMessage().equals("Domain does not exist in system right now.")) {
                //if the member not logged in. check if we need to save notification.
                if (Security.getInstance().isMember(name)) {
                    addNotification(name, new DelayedNotifications(not.getMessage()));
                }
            }
            throw e;
        }
    }

    public void sendAppointmentRemovedNotification(String name,String shopsName) throws MarketException {

        try {

            RealTimeNotifications not = new RealTimeNotifications();
            not.createShopPermissionDeniedMessage(name, shopsName);
            sendImmediateNotification(name, not);
        } catch (Exception e) {
            throw e;
        }
    }
    public void sendItemBaughtNotificationsBatch(ArrayList<String> name, String shopName, ArrayList<String> itemName, ArrayList<Double> price) throws MarketException {

        try {

            RealTimeNotifications not = new RealTimeNotifications();
            for (int i = 0; i < itemName.size(); i++) {

                for (String owner : name) {
                    not.createBuyingOfferMessage(owner, shopName, itemName.get(i), price.get(i));
                    sendImmediateNotification(owner, not);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void sendShopClosedBatchNotificationsBatch(ArrayList<String> name, String shopName) throws MarketException {

        try {

            RealTimeNotifications not = new RealTimeNotifications();
            not.createShopClosedMessage(shopName);
            for (int i = 0; i < name.size(); i++) {

                sendImmediateNotification(name.get(i), not);

            }

        }
        catch (Exception e){
        }
    }

    public void sendShopReOpenedBatchNotificationsBatch(ArrayList<String> name, String shopName) throws MarketException {

        try {

            RealTimeNotifications not = new RealTimeNotifications();
            not.createShopOpenedMessage(shopName);
            for (int i = 0; i < name.size(); i++) {

                sendImmediateNotification(name.get(i), not);

            }
        } catch (Exception e) {
            throw e;
        }
    }

    public void sendShopClosedPermanentlyBatchNotificationsBatch(ArrayList<String> name, String shopName) throws MarketException {

        try {

            RealTimeNotifications not = new RealTimeNotifications();
            not.createShopClosedPermanentlyMessage(shopName);
            for (int i = 0; i < name.size(); i++) {
                sendImmediateNotification(name.get(i), not);

            }
        } catch (Exception e) {
            throw e;
        }
    }

    //Method to send all delayed notifications when the member logged in.
    public void sendDelayedNotification(String name, DelayedNotifications not) throws MarketException {

        try {

            //get the socket name of a member/visitor.
            String address = getAddress(name);
            //send the notification to the given address.
            //TODO: send to publisher.


        } catch (Exception e) {
            if (e.getMessage().equals("Domain does not exist in system right now.")) {
                //if the member not logged in. check if we need to save notification.
                if (Security.getInstance().isMember(name)) {
                    addNotification(name, new DelayedNotifications(not.getMessage()));
                }
            }
            throw e;
        }
    }

    //sends the notifications to member that just logged in.
    public void sendAllNotifications(String memberName) throws MarketException {

        List<DelayedNotifications> nots = getNotifications(memberName);
        for(DelayedNotifications not : nots){
            sendDelayedNotification(memberName,not);
        }
    }
    //Method to update the member name in the map when the member is logged in.
    public void updateName(String Name, String memberName) throws MarketException {

        if (memberNameToSocketName.containsKey(Name)) {
            memberNameToSocketName.remove(Name);
            memberNameToSocketName.put(memberName, Name);
        }
        //after the member logged in, send its notifications.
        sendAllNotifications(memberName);
    }
}



   
