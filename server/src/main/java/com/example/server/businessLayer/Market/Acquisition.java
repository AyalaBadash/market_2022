package com.example.server.businessLayer.Market;


import com.example.server.businessLayer.Payment.PaymentServiceProxy;
import com.example.server.businessLayer.Publisher.NotificationHandler;
import com.example.server.businessLayer.Supply.Address;
import com.example.server.businessLayer.Payment.PaymentMethod;
import com.example.server.businessLayer.Supply.SupplyServiceProxy;
import com.example.server.businessLayer.Market.ResourcesObjects.ErrorLog;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.Users.Member;
import com.example.server.businessLayer.Market.Users.UserController;
import com.example.server.businessLayer.Market.Users.Visitor;
import com.example.server.dataLayer.entities.DalAcquisition;
import com.example.server.dataLayer.entities.DalShoppingCart;
import com.example.server.dataLayer.repositories.AcquisitionRep;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

import javax.persistence.*;

@Entity
public class Acquisition {
    @Id
    @GeneratedValue
    private long id;
    private boolean paymentDone;
    private boolean supplyConfirmed;
    @OneToOne (cascade = CascadeType.MERGE)
    ShoppingCart shoppingCartToBuy;
    String buyerName;
    int supplyID;
    int paymentID;
    private static AcquisitionRep acquisitionRep;

    public Acquisition(ShoppingCart shoppingCartToBuy, String buyerName) {
        this.shoppingCartToBuy = shoppingCartToBuy;
        this.buyerName = buyerName;
        paymentDone = false;
        supplyConfirmed = false;
        acquisitionRep.save(this);
    }

    public Acquisition(){}

    public ShoppingCart buyShoppingCart(NotificationHandler publisher, double expectedPrice, PaymentMethod paymentMethod, Address address, PaymentServiceProxy paymentHandler, SupplyServiceProxy supplyHandler,boolean test) throws MarketException, Exception {

        // checks the price is correct
        //todo: check why there is not an exception here.
        address.setAddress("address");
        address.setZip("4336203");
        address.setCity("beer seva");
        address.setCountry("israel");
        address.setName("shaked");

        if(!isPriceCorrect(publisher,expectedPrice,test))
            return shoppingCartToBuy;

        if(address==null){
            throw new MarketException("Address not supplied.");
        }
        if(!address.isLegal()){
            throw new MarketException("Address details are illegal.");
        }
//        supplyID=supplyHandler.supply(address);
        supplyID = 3;
        if(supplyID==-1){
            shoppingCartToBuy.cancelShopSave();
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Supply has been failed.");
            throw new MarketException("supply has been failed. shopping cart did not change");
        }
        supplyConfirmed = true;
        if(paymentMethod==null){
            throw new MarketException("Payment method not supplied.");
        }
//        if(!paymentMethod.isLegal()){
//            throw new MarketException("Payment method details are illegal.");
//        }
//        paymentID = paymentHandler.pay(paymentMethod);
        paymentID = 4;
        if(paymentID==-1){
            shoppingCartToBuy.cancelShopSave();
            supplyHandler.cancelSupply(supplyID);
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Payment has been failed.");
            throw new MarketException("payment has been failed. shopping cart did not change and supply was canceled");
        }
        paymentDone = true;
        Visitor visitor = UserController.getInstance().getVisitor(buyerName);
        Member member = visitor.getMember ();
        double actualPrice = 0;
        if( member != null) {
            //todo - add discount calculation
            for (Map.Entry<Shop,ShoppingBasket> entry:shoppingCartToBuy.getCart().entrySet())
            {
                Shop currShop = entry.getKey();
                if (!currShop.getPurchasePolicy().isPoliciesHeld(entry.getValue())){
                    throw new MarketException("One of the baskets does not match its shop policy");
                }
                actualPrice = actualPrice + currShop.getDiscountPolicy().calculateDiscount(entry.getValue());
            }
            AcquisitionHistory acq = new AcquisitionHistory(shoppingCartToBuy, member.getName(), actualPrice, expectedPrice);
            if (true)
                throw new MarketException("toDelete");
            member.savePurchase(acq);
        }
        shoppingCartToBuy.clear();
        acquisitionRep.save(this);
        return shoppingCartToBuy;
    }

    private  boolean isPriceCorrect(NotificationHandler publisher, double expectedPrice,boolean test) throws MarketException {
        double actualPrice;
        try {
            actualPrice = shoppingCartToBuy.saveFromShops(publisher,buyerName,test);
        }catch (MarketException e){
            return false;
        }
        if (Math.abs ( actualPrice - expectedPrice ) > 0.001){
            shoppingCartToBuy.cancelShopSave();
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Shopping cart price has been changed for a costumer");
            return false;
        }
        return true;
    }


    public boolean isPaymentDone() {
        return paymentDone;
    }

    public void setPaymentDone(boolean paymentDone) {
        this.paymentDone = paymentDone;
    }

    public boolean isSupplyConfirmed() {
        return supplyConfirmed;
    }

    public void setSupplyConfirmed(boolean supplyConfirmed) {
        this.supplyConfirmed = supplyConfirmed;
    }

    public ShoppingCart getShoppingCartToBuy() {
        return shoppingCartToBuy;
    }

    public void setShoppingCartToBuy(ShoppingCart shoppingCartToBuy) {
        this.shoppingCartToBuy = shoppingCartToBuy;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void setAcquisitionRep(AcquisitionRep acquisitionRep) {
        Acquisition.acquisitionRep = acquisitionRep;
    }
}
