package com.example.server.serviceLayer.Notifications;

import com.example.server.businessLayer.Market.Users.Member;

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
        message= "Unfortunately, your appointment as "+ permission+" in shop "+shop +" has been canceled.";
    }
    public void createNewMessageMessage(){
        message= "You have a new message in your inbox.";
    }
    public void createAnotherMessage(String mess){
        message= mess;
    }


    public void createNewManagerMessage(String shopOwner, String appointed,String shop) {
        message= "you got appointed as manager to shop "+shop+ " by "+shopOwner;
    }

    public void createNewOwnerMessage(String shopOwner, String appointed, String shopName) {
        message= "you got appointed as owner to shop "+shopName+ " by "+shopOwner;
    }

    public void createNewOfferOfBidMessage(String buyer, String itemName, double newPrice, String shopName) {
        message = String.format ("Hello %s,\n Your bid for %s in shop %s has received a counter-bid on the price %f. ", buyer, itemName, shopName, newPrice);
    }

    public void createBidApprovedMessage(String buyer, String itemName, double price, String shopName) {
        message = String.format ("Hello %s,\n Your bid for %s in shop %s at a price of %f has been approved. ", buyer, itemName, shopName, price);
    }

    public void createBidRejectedMessage(String buyer, String itemName, double price, String shopName) {
        message = String.format ("Hello %s,\n Your bid for %s in shop %s at a price of %f has been rejected. ", buyer, itemName, shopName, price);
    }

    public void createBidRejectedToApprovesMessage(String buyer, double price, String itemName, String shopName) {
        message = String.format ("Hello,\n The bid for %s in shop %s at a price of %f from %s has been rejected. ", itemName, shopName, price, buyer);
    }

    public void createNewOfferOfBidToApprovalOfApprovesMessage(String buyer, double newPrice, String itemName, String shopName) {
        message = String.format ("Hello,\n The bid for %s in shop %s from %s got a counter-bid at a price of %f. ", itemName, shopName, buyer, newPrice);
    }

    public void createNewBidToApprovalOfApprovesMessage(String buyer, double newPrice, String itemName, String shopName) {
        message = String.format ("Hello,\n A new bid for %s in shop %s at a price of %f from %s has been created.", itemName, shopName, newPrice, buyer);
    }

    public void createNewBidCanceledToApprovesMessage(String buyer, double price, String itemName, String shopName) {
        message = String.format ("Hello,\n The bid for %s in shop %s from %s at a price of %f has been cancelled by the buyer.", itemName, shopName, buyer, price);
    }
}
