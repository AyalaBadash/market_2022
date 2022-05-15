package com.example.server.businessLayer;

import com.example.server.serviceLayer.FacadeObjects.OutputMessage;
import com.example.server.serviceLayer.MessageController;
import com.example.server.serviceLayer.Notifications.DelayedNotifications;
import com.example.server.serviceLayer.Notifications.RealTimeNotifications;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class Publisher{

    //singleton to avoid resending notifications and to control all notifications from one instance of one object.
    private static Publisher instance= null;

    //holds notifications to send to each member
    private Map<String, List<DelayedNotifications>> membersNotifications;

    private Map<String, Void> memberToDomain;
    private MessageController controller;
    private Publisher(){
        membersNotifications= new ConcurrentHashMap<>();
        memberToDomain=new ConcurrentHashMap<>();
        controller= new MessageController();
    }


    public static synchronized Publisher getInstance(){

        if(instance==null){
            instance=new Publisher();
        }
        return instance;
    }


    public void addNotification(String memberName , DelayedNotifications not){
        if(!membersNotifications.containsKey(memberName)){
            membersNotifications.put(memberName,new ArrayList<>());
        }
        membersNotifications.get(memberName).add(not);
    }

        public List<DelayedNotifications> getNotifications(String memberName){
        if(!membersNotifications.containsKey(memberName)){
            return new ArrayList<>();
        }
        else {
            List<DelayedNotifications> ret=membersNotifications.get(memberName);
            membersNotifications.remove(memberName);
            return ret;
        }
    }
    public String getAddress(String domain) throws MarketException {
        if(!memberToDomain.containsKey(domain)){
            throw new MarketException("Domain does not exist in system right now.");
        }
        return domain;
    }
    public boolean isExists(String user){
        return memberToDomain.containsKey(user);
    }
    public void addAddress(String name){
        if(memberToDomain.containsKey(name)){
            memberToDomain.remove(name);
        }
        memberToDomain.put(name,null);
    }
    public void removeAddress(String name){

        if(memberToDomain.containsKey(name)) {

            memberToDomain.remove(name);
        }

    }

    private void sendImmediateNotification(String name , RealTimeNotifications not) throws MarketException {

        try{

            if(memberToDomain.containsKey(name) ) {
                String address =getAddress(name);
                //send the notification to the given address.
                controller.sendMessage(name,new OutputMessage(not.getMessage()));
            }
            else{
                //real time notifications will not be saved for non-connected visitor.
                throw new MarketException("There is no address to send this notification right now.");
            }
        }
        catch (Exception e){
            throw e;
        }
    }
    public void sendItemBaughtNotificationsBatch(ArrayList<String> name ,String shopName, ArrayList<String> itemName, ArrayList<Double> price) throws MarketException {

        try{

            RealTimeNotifications not = new RealTimeNotifications();
            for(int i =0; i< itemName.size() ; i++) {

                for(String owner : name) {
                    not.createBuyingOfferMessage(owner, shopName, itemName.get(i), price.get(i));
                    sendImmediateNotification(owner, not);
                }
            }
        }
        catch (Exception e){
            throw e;
        }
    }
    public void sendShopClosedBatchNotificationsBatch(ArrayList<String> name ,String shopName) throws MarketException {

        try{

            RealTimeNotifications not = new RealTimeNotifications();
            not.createShopClosedMessage(shopName);
            for(int i =0; i< name.size() ; i++) {

                sendImmediateNotification(name.get(i), not);

            }
        }
        catch (Exception e){
            throw e;
        }
    }
    public void sendShopReopenedBatchNotificationsBatch(ArrayList<String> name ,String shopName) throws MarketException {

        try{

            RealTimeNotifications not = new RealTimeNotifications();
            not.createShopOpenedMessage(shopName);
            for(int i =0; i< name.size() ; i++) {

                sendImmediateNotification(name.get(i), not);

            }
        }
        catch (Exception e){
            throw e;
        }
    }
    public void sendShopClosedPermanentlyBatchNotificationsBatch(ArrayList<String> name ,String shopName) throws MarketException {

        try{

            RealTimeNotifications not = new RealTimeNotifications();
            not.createShopClosedPermanentlyMessage(shopName);
            for(int i =0; i< name.size() ; i++) {
                sendImmediateNotification(name.get(i), not);

            }
        }
        catch (Exception e){
            throw e;
        }
    }
    public void sendDelayedNotification(String name,DelayedNotifications not ) throws MarketException {

        try{

            //first try to find the address. else add to list.
            if(memberToDomain.containsKey(name)) {
                String address = getAddress(name);
                //send the notification to the given address.
                controller.sendMessage(name,new OutputMessage(not.getMessage()));
            }
            else{
                addNotification(name, not);
            }

        }
        catch (Exception e){
            throw e;
        }
    }


    public Collection<String> getUsers() {
        return memberToDomain.keySet();
    }

    public void updateName(String userName, String name) {

        if(memberToDomain.containsKey(userName)){
            memberToDomain.remove(userName);
            memberToDomain.put(name,null);
        }
    }
}
