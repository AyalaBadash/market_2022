
package com.example.server.businessLayer.Market;


import com.example.server.businessLayer.Payment.PaymentServiceProxy;
import com.example.server.businessLayer.Publisher.Publisher;
import com.example.server.businessLayer.Supply.Address;
import com.example.server.businessLayer.Payment.PaymentMethod;
import com.example.server.businessLayer.Supply.SupplyServiceProxy;
import com.example.server.businessLayer.Market.ResourcesObjects.ErrorLog;
import com.example.server.businessLayer.Market.ResourcesObjects.MarketException;
import com.example.server.businessLayer.Market.Users.Member;
import com.example.server.businessLayer.Market.Users.UserController;
import com.example.server.businessLayer.Market.Users.Visitor;
import com.fasterxml.jackson.core.JsonProcessingException;

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

    public ShoppingCart buyShoppingCart(Publisher publisher,double expectedPrice, PaymentMethod paymentMethod, Address address, PaymentServiceProxy paymentServiceProxy, SupplyServiceProxy supplyServiceProxy) throws MarketException, JsonProcessingException {
        // checks the price is correct
       if(!isPriceCorrect(publisher,expectedPrice))
           return shoppingCartToBuy;

        supplyID= supplyServiceProxy.supply(address);
        if(supplyID==-1){
            shoppingCartToBuy.cancelShopSave();
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Supply has been failed.");
            throw new MarketException("supply has been failed. shopping cart did not change");
        }
        paymentID = pay(paymentMethod, paymentServiceProxy);
        if(paymentID==-1){
            shoppingCartToBuy.cancelShopSave();
            supplyServiceProxy.cancelSupply(supplyID);
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

    private  boolean isPriceCorrect(Publisher publisher, double expectedPrice) throws MarketException {
        double actualPrice = shoppingCartToBuy.saveFromShops(publisher,buyerName);
        if (actualPrice != expectedPrice){
            shoppingCartToBuy.cancelShopSave();
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Shopping cart price has been changed for a costumer");
            return false;
        }
        return true;
    }

    private int pay(PaymentMethod paymentMethod, PaymentServiceProxy paymentServiceProxy) throws JsonProcessingException {

        return paymentServiceProxy.pay(paymentMethod);

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
