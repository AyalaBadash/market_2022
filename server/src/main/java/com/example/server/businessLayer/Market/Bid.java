package com.example.server.businessLayer.Market;

import com.example.server.businessLayer.Market.ResourcesObjects.DebugLog;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketConfig;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.dataLayer.repositories.BidRep;
import javax.persistence.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Bid {
    @Id
    @GeneratedValue
    private long id;
    public enum Side{
        buyer,
        seller,
        both;
    }
    private String buyerName;
    private boolean isMember;
    private Integer itemId;
    private double amount;
    private double price;
    @Enumerated(EnumType.STRING)
    private Side sideNeedToApprove;
    @Enumerated(EnumType.STRING)
    private Side suggester;
    private boolean approved;
    @ElementCollection
    @CollectionTable(name = "shopOwnersStatus")
    @Column(name="status")
    @MapKeyColumn(name="name")
    private Map<String, Boolean> shopOwnersStatus;
    private static BidRep bidRep;
    public Bid(String buyerName, boolean isMember, Integer itemId, double price, double amount, List<String> shopOwners) {
        this.buyerName = buyerName;
        this.isMember = isMember;
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
        if (!MarketConfig.IS_TEST_MODE) {
            bidRep.save(this);
        }
        //todo - notify shop owners
    }

    public Bid() {
    }

    public boolean equals(Object object){
        if(object instanceof Bid){
            Bid bid = (Bid) object;
            return (bid.buyerName.equals ( this.buyerName ) && bid.itemId == this.itemId);
        }
        return false;
    }

    public void addApproves(String name) {
        shopOwnersStatus.put ( name, false );
        if (approved)
            approved = false;
        if (!MarketConfig.IS_TEST_MODE) {
            bidRep.save(this);

        }
    }

    public void removeApproves(String firedAppointed) throws MarketException {
        if(!shopOwnersStatus.containsKey (firedAppointed)){
            throw new MarketException ( "This member does not need to approve, so cannot be removed" );
        }
        shopOwnersStatus.remove ( firedAppointed );
        if(isApproved () && sideNeedToApprove != Side.buyer)
            approved = true;
        if (!MarketConfig.IS_TEST_MODE) {
            bidRep.save(this);
        }
    }

    public synchronized boolean approveBid(String name) throws MarketException {
        if(name.equals ( buyerName )) {
            if (sideNeedToApprove.equals(Side.buyer))
                return true;
            return false;
        }
        if(!shopOwnersStatus.containsKey (name)){
            throw new MarketException ( "You do not have the permission to approve the bid" );
        }
        if(sideNeedToApprove.equals ( Side.seller ) || sideNeedToApprove.equals ( Side.both )) {
            shopOwnersStatus.replace ( name, true );
            if (!MarketConfig.IS_TEST_MODE) {
                bidRep.save(this);
            }
            if (isApproved ( )) {
                if(sideNeedToApprove.equals ( Side.buyer )){
                    return false;
                }
                approved = true;
                if (!MarketConfig.IS_TEST_MODE) {
                    bidRep.save(this);
                }
                return true;
            }
            return false;
        }
        return false;
    }

    public void rejectBid(String name) throws MarketException {
        if( !name.equals ( buyerName ) && sideNeedToApprove.equals ( Side.buyer )){
            DebugLog.getInstance ().Log ( "bid has already been approved." );
            throw new MarketException ( "bid has already been approved." );
        } else if(name.equals ( buyerName ) && ! sideNeedToApprove.equals ( Side.buyer )) {
            DebugLog.getInstance ().Log ( "buyer tried to reject its own bid." );
            throw new MarketException ( "You cannot reject your own bid, if you want you can cancel it." );
        } if(!name.equals ( buyerName ) && !shopOwnersStatus.containsKey ( name )){
            DebugLog.getInstance ().Log ( "cannot reject the bid." );
            throw new MarketException ( "cannot reject the bid.");
        }
    }

    public synchronized void suggestNewOffer(String name, Double newPrice) throws MarketException {
        if(sideNeedToApprove.equals ( Side.buyer ) || name.equals ( buyerName )){
            DebugLog.getInstance ().Log ( "This bid is not for you to approve." );
            throw new MarketException ( "This bid is not for you to approve." );
        }
        if (!shopOwnersStatus.containsKey ( name )){
            DebugLog.getInstance ().Log ( "This bid is not for you to offer a new bid." );
            throw new MarketException ( "This bid is not for you to offer a new bid." );
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
        if (!MarketConfig.IS_TEST_MODE) {
            bidRep.save(this);
        }
    }

    public synchronized void setNewPrice(double newPrice) {
        for(String shopOwnerName : shopOwnersStatus.keySet ()){
            shopOwnersStatus.replace ( shopOwnerName, false );
        }
        this.price = newPrice;
        if (!MarketConfig.IS_TEST_MODE) {
            bidRep.save(this);
        }
    }

    public boolean isApproved() {
        for ( Boolean status : shopOwnersStatus.values () )
            if (!status)
                return false;
        if(sideNeedToApprove.equals ( Side.both ))
            sideNeedToApprove = Side.buyer;
        if (!MarketConfig.IS_TEST_MODE) {
            bidRep.save(this);
        }
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
    public boolean getApproved(){
        return this.approved;
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

    public Map<String, Boolean> getShopOwnersStatus() {
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

    public boolean isMember() {
        return isMember;
    }

    public void setMember(boolean member) {
        isMember = member;
    }

    public static BidRep getBidRep() {
        return bidRep;
    }

    public static void setBidRep(BidRep bidRep) {
        Bid.bidRep = bidRep;
    }
}
