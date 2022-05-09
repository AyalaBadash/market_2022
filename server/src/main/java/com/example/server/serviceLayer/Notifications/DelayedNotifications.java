package com.example.server.serviceLayer.Notifications;

public class DelayedNotifications extends Notification{


    public DelayedNotifications(){
        super();
    }
    public void createShopClosedMessage(String shopName){
        message= "The shop" + shopName+ " was closed";
    }

    public void createShopClosedPermanentlyMessage(String shopName){
        message= "The shop" + shopName+ " was closed permanently by the market manager";
    }

    public void createShopOpenedMessage(String shopName){
        message= "The shop" + shopName+ " was reopened";
    }

    public void createOfferAcceptedMessage( String shopName ,String product, int price){
        message= "Your offer to buy "+product+" from "+ shopName+ " for the amount of "+price+ " accepted!";
    }
    public void createOfferDeclinedMessage( String shopName ,String product, int price){
        message= "Your offer to buy "+product+" from "+ shopName+ " for the amount of "+price+ " declined!";
    }

}
