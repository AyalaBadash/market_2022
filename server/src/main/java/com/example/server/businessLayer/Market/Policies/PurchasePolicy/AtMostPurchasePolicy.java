package com.example.server.businessLayer.Market.Policies.PurchasePolicy;

import com.example.server.businessLayer.Market.ShoppingBasket;
import com.example.server.businessLayer.Market.Users.Visitor;

public class AtMostPurchasePolicy extends PurchasePolicy{
    private double amount;
    @Override
    public boolean isPolicyHeld(Visitor visitor, ShoppingBasket shoppingBasket) {
        return false;
    }
}
