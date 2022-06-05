package com.example.server.businessLayer.Market;

import com.example.server.businessLayer.Payment.PaymentHandler;

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
import com.fasterxml.jackson.core.JsonProcessingException;

public class Acquisition {
    private boolean paymentDone; //save
    private boolean supplyConfirmed; //save
    ShoppingCart shoppingCartToBuy;
    String buyerName;
    int supplyID;
    int paymentID;

    public Acquisition(ShoppingCart shoppingCartToBuy, String buyerName) {
        this.shoppingCartToBuy = shoppingCartToBuy;
        this.buyerName = buyerName;
        paymentDone = false;
        supplyConfirmed = false;
    }

    public ShoppingCart buyShoppingCart(NotificationHandler publisher, double expectedPrice, PaymentMethod paymentMethod, Address address, PaymentServiceProxy paymentHandler, SupplyServiceProxy supplyHandler) throws MarketException, Exception {

        // checks the price is correct
        //todo: check why there is not an exception here.
        if(!isPriceCorrect(publisher,expectedPrice))
            return shoppingCartToBuy;

        if(address==null){
            throw new MarketException("Address not supplied.");
        }
        if(!address.isLegal()){
            throw new MarketException("Address details are illegal.");
        }
        supplyID=supplyHandler.supply(address);
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
        if(!paymentMethod.isLegal()){
            throw new MarketException("Payment method details are illegal.");
        }
        paymentID = paymentHandler.pay(paymentMethod);
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
        if( member != null) {
            //todo - add discount calculation
            AcquisitionHistory acq = new AcquisitionHistory(shoppingCartToBuy, member.getName(), expectedPrice, expectedPrice);
            member.savePurchase(acq);
        }
        shoppingCartToBuy.clear();
        return shoppingCartToBuy;
    }

    private  boolean isPriceCorrect(NotificationHandler publisher, double expectedPrice) throws MarketException {
        double actualPrice;
        try {
            actualPrice = shoppingCartToBuy.saveFromShops(publisher,buyerName);
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
    private DalAcquisition toDalObject(){
        //TODO check this code
        int cartID = this.shoppingCartToBuy.toDalObject().getId();
        return new DalAcquisition(cartID,this.buyerName,this.paymentDone,this.supplyConfirmed,this.supplyID,this.paymentID);
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
}
