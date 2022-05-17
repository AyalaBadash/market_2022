package com.example.server.businessLayer;

import com.example.server.ResourcesObjects.*;
import com.example.server.businessLayer.ExternalComponents.PaymentService;
import com.example.server.businessLayer.ExternalComponents.ProductsSupplyService;
import com.example.server.businessLayer.Users.Member;
import com.example.server.businessLayer.Users.UserController;
import com.example.server.businessLayer.Users.Visitor;

import java.time.LocalDateTime;

public class Acquisition {
    private boolean paymentDone;
    private boolean supplyConfirmed;
    ShoppingCart shoppingCartToBuy;
    String buyerName;
    String supplyID;
    String paymentID;

    public Acquisition(ShoppingCart shoppingCartToBuy, String buyerName) {
        this.shoppingCartToBuy = shoppingCartToBuy;
        this.buyerName = buyerName;
        paymentDone = false;
        supplyConfirmed = false;
    }

    public ShoppingCart buyShoppingCart(double expectedPrice, PaymentMethod paymentMethod, Address address, PaymentService paymentService, ProductsSupplyService supplyService) throws MarketException {
        // checks the price is correct
       if(!isPriceCorrect(expectedPrice))
           return shoppingCartToBuy;
        boolean supplyIsPossible = supply(supplyService, address);
        if(!supplyIsPossible){
            shoppingCartToBuy.cancelShopSave();
            ErrorLog errorLog = ErrorLog.getInstance();
            errorLog.Log("Supply has been failed.");
            throw new MarketException("supply has been failed. shopping cart did not change");
        }
        boolean isPaymentPossible = pay(paymentMethod, paymentService);
        if(!isPaymentPossible){
            shoppingCartToBuy.cancelShopSave();
            supplyService.cancelSupply(supplyID);
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

    private boolean pay(PaymentMethod paymentMethod, PaymentService paymentService){
        paymentID = paymentService.pay(paymentMethod);
        if(paymentID.equals("-1"))
            return false;
        return true;
    }

    private boolean supply(ProductsSupplyService supplyService, Address address){
        supplyID = supplyService.supply(address, LocalDateTime.now());
        if(supplyID.equals("-1"))
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
