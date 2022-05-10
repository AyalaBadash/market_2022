package com.example.server.businessLayer;

import com.example.server.serviceLayer.Notifications.DelayedNotifications;
import com.example.server.serviceLayer.Notifications.RealTimeNotifications;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Publisher {

    //singleton to avoid resending notifications and to control all notifications from one instance of one object.
    private static Publisher instance= null;

    //holds notifications to send to each member
    private Map<String, List<DelayedNotifications>> membersNotifications;

    private Map<String, String> memberToDomain;
    private Map <String ,String>  addressBook;
    private Publisher(){
        membersNotifications= new ConcurrentHashMap<>();
        addressBook= new ConcurrentHashMap<>();
        memberToDomain=new ConcurrentHashMap<>();
    }


    public static Publisher getInstance(){

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

        public List<DelayedNotifications> getMembersNotifications(String memberName){
        if(!membersNotifications.containsKey(memberName)){
            return new ArrayList<>();
        }
        else {
            List<DelayedNotifications> ret=membersNotifications.get(memberName);
            membersNotifications.remove(memberName);
            return ret;
        }
    }
    public String getAddressByDomain(String domain) throws MarketException {
        if(!addressBook.containsKey(domain)){
            throw new MarketException("Domain does not exist in system right now.");
        }
        return addressBook.get(domain);
    }
    public void addAddressToBook(String name,String domain, String address){
        if(memberToDomain.containsKey(name)){
            memberToDomain.remove(name);
            addressBook.remove(domain);
        }
        memberToDomain.put(name,domain);
        addressBook.put(domain, address);
    }
    public void removeAddress(String name){

        if(memberToDomain.containsKey(name)) {

            String dom= memberToDomain.get(name);
            memberToDomain.remove(name);
            addressBook.remove(dom);
        }

    }

    private void sendImmediateNotification(String name , RealTimeNotifications not) throws MarketException {

        //TODO : finish implement when the websocket is ready.
        try{

            if(memberToDomain.containsKey(name) && addressBook.containsKey(memberToDomain.get(name))) {
                String address = addressBook.get(memberToDomain.get(name));
                //send the notification to the given address.
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

        //TODO : finish implement when the websocket is ready.
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

        //TODO : finish implement when the websocket is ready.
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

        //TODO : finish implement when the websocket is ready.
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

        //TODO : finish implement when the websocket is ready.
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
    public void sendDelayedNotification(String name,DelayedNotifications not ){

        //TODO : finish implement when the websocket is ready.
        try{

            //first try to find the address. else add to list.
            if(memberToDomain.containsKey(name) && addressBook.containsKey(memberToDomain.get(name))) {
                String address = addressBook.get(memberToDomain.get(name));
                //send the notification to the given address.
            }
            else{
                addNotification(name, not);
            }

        }
        catch (Exception e){
            throw e;
        }
    }

}
