package com.example.server.serviceLayer.Notifications;

public class RealTimeNotifications extends Notification{

    public RealTimeNotifications(){
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

    public void createBuyingOfferMessage(String offer , String shopName ,String product, double price){
        message= offer+ " submitted a buying offer in the shop "+ shopName+", to buy "+ product+ " for the amount of "+price;
    }
    public void createMembershipDeniedMessage(){
        message= "Unfortunately, your membership has been denied.";
    }
    public void createShopPermissionDeniedMessage(String shop,String permission){
        message= "Unfortunately, your appointment as "+ permission+" in shop "+shop +"has been canceled.";
    }
    public void createNewMessageMessage(){
        message= "You have a new message in your inbox.";
    }
    public void createAnotherMessage(String mess){
        message= mess;
    }


}
