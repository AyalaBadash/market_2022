package com.example.server.businessLayer.Market;

import com.example.server.businessLayer.Market.ResourcesObjects.DebugLog;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Bid {


    public enum Side{
        buyer,
        seller,
        both;

    }
    private String buyerName;
    private Integer itemId;
    private double amount;
    private double price;
    private Side sideNeedToApprove;
    private Side suggester;
    private boolean approved;
    private HashMap<String, Boolean> shopOwnersStatus;
    public Bid(String buyerName, Integer itemId, double price, double amount, List<String> shopOwners) {
        this.buyerName = buyerName;
        this.itemId = itemId;
        this.price = price;
        this.amount = amount;
        this.sideNeedToApprove = Side.seller;
        this.suggester = Side.buyer;
        shopOwnersStatus = new LinkedHashMap<> (  );
        for(String shopOwnerName: shopOwners){
            shopOwnersStatus.put ( shopOwnerName, false );
        }
        this.approved = false;
        //todo - notify shop owners
    }

    public Bid() {
    }

    public void addApproves(String name) {
        shopOwnersStatus.put ( name, false );
        if (approved)
            approved = false;
    }

    public void removeApproves(String firedAppointed) {
        shopOwnersStatus.remove ( firedAppointed );
        if(isApproved () && sideNeedToApprove != Side.buyer)
            approved = true;
    }

    public synchronized boolean approveBid(String name){
        if(name.equals ( buyerName ) && sideNeedToApprove.equals ( Side.buyer ))
            return true;
        if(sideNeedToApprove.equals ( Side.seller ) || sideNeedToApprove.equals ( Side.both )) {
            shopOwnersStatus.replace ( name, true );
            if (isApproved ( )) {
                if(sideNeedToApprove.equals ( Side.buyer )){
                    //todo - notify visitor name in the new offer
                    return false;
                }
                //todo - notify visitor name he got the bid - if not exist cancel bid
                approved = true;
                return true;
            }
            return false;
        }
        return false;
    }

    public void rejectBid(String name){
        if( !name.equals ( buyerName ) && sideNeedToApprove.equals ( Side.seller )){
            //todo - notify visitor
        } else {
            //todo - cancel bid. delete from db? need to send notification to sellers?
        }
    }

    public synchronized void suggestNewOffer(String name, Double newPrice) throws MarketException {
        if(!name.equals ( buyerName ) && sideNeedToApprove.equals ( Side.buyer ) || name.equals ( buyerName ) && (sideNeedToApprove.equals ( Side.seller ) || sideNeedToApprove.equals ( Side.both ))){
            DebugLog.getInstance ().Log ( "This bid is not for you to approve." );
            throw new MarketException ( "This bid is not for you to approve." );
        }
        if(newPrice <= 0){
            DebugLog.getInstance ().Log ( "price suggested cannot be negative." );
            throw new MarketException ( "price suggested cannot be negative." );
        }
        if(newPrice.equals ( this.price )){
            DebugLog.getInstance ().Log ( "the new offer is equal to the offer offered to you." );
            throw new MarketException ( "the new offer is equal to the offer offered to you." );
        }
        setNewPrice ( newPrice);
        if(name.equals ( buyerName )){
            sideNeedToApprove = Side.seller;
        }else {
            sideNeedToApprove = Side.both;
            approveBid ( name );
        }
    }

    public synchronized void setNewPrice(double newPrice) {
        for(String shopOwnerName : shopOwnersStatus.keySet ()){
            shopOwnersStatus.replace ( shopOwnerName, false );
        }
        //todo - notify all shop owners on the new bid
        this.price = newPrice;
    }

    public boolean isApproved() {
        for ( Boolean status : shopOwnersStatus.values () )
            if (!status)
                return false;
        if(sideNeedToApprove.equals ( Side.both ))
            sideNeedToApprove = Side.buyer;
        return true;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public double getPrice() {
        return price;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Side getSideNeedToApprove() {
        return sideNeedToApprove;
    }

    public void setSideNeedToApprove(Side sideNeedToApprove) {
        this.sideNeedToApprove = sideNeedToApprove;
    }

    public Side getSuggester() {
        return suggester;
    }

    public void setSuggester(Side suggester) {
        this.suggester = suggester;
    }

    public HashMap<String, Boolean> getShopOwnersStatus() {
        return shopOwnersStatus;
    }

    public void setShopOwnersStatus(HashMap<String, Boolean> shopOwnersStatus) {
        this.shopOwnersStatus = shopOwnersStatus;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
