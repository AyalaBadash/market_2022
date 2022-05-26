package com.example.server.businessLayer;

import com.example.server.ResourcesObjects.*;
import com.example.server.businessLayer.ExternalComponents.Payment.PaymentHandler;
import com.example.server.businessLayer.ExternalComponents.Supply.Address;
import com.example.server.businessLayer.ExternalComponents.Payment.PaymentMethod;
import com.example.server.businessLayer.ExternalComponents.Supply.SupplyHandler;
import com.example.server.businessLayer.ExternalComponents.Supply.SupplyService;
import com.example.server.businessLayer.Users.Member;
import com.example.server.businessLayer.Users.UserController;
import com.example.server.businessLayer.Users.Visitor;

import java.time.LocalDateTime;

public class Acquisition {
    private boolean paymentDone;
    private boolean supplyConfirmed;
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

    public ShoppingCart buyShoppingCart(double expectedPrice, PaymentMethod paymentMethod, Address address, PaymentHandler paymentHandler, SupplyHandler supplyHandler) throws MarketException {
        // checks the price is correct
       if(!isPriceCorrect(expectedPrice))
           return shoppingCartToBuy;

        if(supplyHandler.supply(address)==-1){
            shoppingCartToBuy.cancelShopSave();
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Supply has been failed.");
            throw new MarketException("supply has been failed. shopping cart did not change");
        }
        boolean isPaymentPossible = pay(paymentMethod, paymentHandler);
        if(!isPaymentPossible){
            shoppingCartToBuy.cancelShopSave();
            supplyHandler.cancelSupply(supplyID);
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Payment has been failed.");
            throw new MarketException("payment has been failed. shopping cart did not change and supply was canceled");
        }
        Visitor visitor = UserController.getInstance().getVisitor(buyerName);
        Member member = visitor.getMember ();
        if( member != null) {
            //todo - add discount calculation
            AcquisitionHistory acq = new AcquisitionHistory(shoppingCartToBuy, member.getName(), expectedPrice, expectedPrice);
            member.savePurchase(acq);
        }
        shoppingCartToBuy.clear();
        return null;
    }

    private  boolean isPriceCorrect(double expectedPrice) throws MarketException {
        double actualPrice = shoppingCartToBuy.saveFromShops(buyerName);
        if (actualPrice != expectedPrice){
            shoppingCartToBuy.cancelShopSave();
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Shopping cart price has been changed for a costumer");
            return false;
        }
        return true;
    }

    //TODO: add address to payment method.
    private boolean pay(PaymentMethod paymentMethod, PaymentHandler paymentHandler){
        Address address=new Address("","","","");
        paymentID = paymentHandler.pay(paymentMethod);
        if(paymentID==-1)
            return false;
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
}
