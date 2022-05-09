package com.example.server.businessLayer;

import com.example.server.serviceLayer.Notifications.DelayedNotifications;
import com.example.server.serviceLayer.Notifications.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Publisher {

    //singleton to avoid resending notifications and to control all notifications from one instance of one object.
    private static Publisher instance= null;

    //holds notifications to send to each member
    private Map<String, List<DelayedNotifications>> membersNotifications;
    private Map <String ,String>  addressBook;
    private Publisher(){
        membersNotifications= new ConcurrentHashMap<>();
        addressBook= new ConcurrentHashMap<>();
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
    public void addAddressToBook(String domain, String address){
        if(addressBook.containsKey(domain)){
            addressBook.remove(domain);
        }
        addressBook.put(domain, address);
    }
    public void removeAddress(String domain){
        addressBook.remove(domain);
    }

}
