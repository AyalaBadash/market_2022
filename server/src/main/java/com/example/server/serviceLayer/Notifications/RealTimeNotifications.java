package com.example.server.serviceLayer.Notifications;

public class RealTimeNotifications extends Notification{

    public RealTimeNotifications(){
        super();
    }

    public void createBuyingOfferMessage(String offer , String shopName ,String product, int price){
        message= offer+ " submitted a buying offer in the shop "+ shopName+", to buy "+ product+ " for the amount of "+price;
    }
    public void createMembershipDeniedMessage(){
        message= "Unfortunately, your membership has been denied.";
    }
    public void createOtherMessage(String mess){
        message= mess;
    }


}
