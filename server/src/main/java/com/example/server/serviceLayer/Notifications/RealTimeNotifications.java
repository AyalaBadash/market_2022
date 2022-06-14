package com.example.server.serviceLayer.Notifications;

import com.example.server.businessLayer.Market.Users.Member;

public class RealTimeNotifications extends Notification{

    public RealTimeNotifications(){
        super();
    }

    public void createShopClosedMessage(String shopName){
        message= "The shop" + shopName+ " was closed"+"\n";
    }

    public void createShopClosedPermanentlyMessage(String shopName){
        message= "The shop" + shopName+ " was closed permanently by the market manager"+"\n";
    }

    public void createShopOpenedMessage(String shopName){
        message= "The shop" + shopName+ " was reopened"+"\n";
    }

    public void createBuyingOfferMessage(String offer , String shopName ,String product, double price){
        message= offer+ " submitted a buying offer in the shop "+ shopName+", to buy "+ product+ " for the amount of "+price+"\n";
    }
    public void createMembershipDeniedMessage(){
        message= "Unfortunately, your membership has been denied."+"\n";
    }
    public void createShopPermissionDeniedMessage(String shop,String permission){
        message= "Unfortunately, your appointment as "+ permission+" in shop "+shop +" has been canceled."+"\n";
    }
    public void createNewMessageMessage(){
        message= "You have a new message in your inbox."+"\n";
    }
    public void createAnotherMessage(String mess){
        message= mess+"\n";
    }


    public void createNewManagerMessage(String shopOwner, String appointed,String shop) {
        message= "you got appointed as manager to shop "+shop+ " by "+shopOwner+"\n";
    }

    public void createNewOwnerMessage(String shopOwner, String appointed, String shopName) {
        message= "you got appointed as owner to shop "+shopName+ " by "+shopOwner+"\n";
    }
}
